package de.larmic.testcontainers.elasticsearch

import de.larmic.testcontainers.elasticsearch.TweetDocument.Companion.documentIndex
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.springframework.boot.CommandLineRunner
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ElasticsearchIndexInitializer(private val restHighLevelClient: RestHighLevelClient,
                                    private val environment: Environment) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (!runningInTestMode()) {
            createIndexIfNotExists(documentIndex)
        }
    }

    private fun runningInTestMode() = environment.activeProfiles.contains("it")

    private fun createIndexIfNotExists(index: String) {
        if (indexDoesNotExists(index)) {
            // see https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.x/java-rest-high-create-index.html
            val createIndexRequest = CreateIndexRequest(index)
            // mapping will be created automatically with first document index
            //createIndexRequest.mapping(type, "message", "type=text")
            restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT)
        }
    }

    private fun indexDoesNotExists(index: String) = !restHighLevelClient.indices().exists(org.elasticsearch.client.indices.GetIndexRequest(index), RequestOptions.DEFAULT)

}
