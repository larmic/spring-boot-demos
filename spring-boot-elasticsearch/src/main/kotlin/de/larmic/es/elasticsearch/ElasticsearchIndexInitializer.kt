package de.larmic.es.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest
import co.elastic.clients.elasticsearch.indices.ExistsRequest
import de.larmic.es.elasticsearch.TweetDocument.Companion.documentIndex
import org.springframework.boot.CommandLineRunner
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ElasticsearchIndexInitializer(private val esClient: ElasticsearchClient,
                                    private val environment: Environment) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (!runningInTestMode()) {
            createIndexIfNotExists(documentIndex)
        }
    }

    private fun runningInTestMode() = environment.activeProfiles.contains("it")

    private fun createIndexIfNotExists(index: String) {
        if (esClient.indexDoesNotExists(index)) {
            esClient.indices().create { c: CreateIndexRequest.Builder -> c.index(documentIndex) }
        }
    }

    private fun ElasticsearchClient.indexDoesNotExists(index: String) = !this.indexExists(index)
    private fun ElasticsearchClient.indexExists(index: String) = this.indices().exists { e: ExistsRequest.Builder -> e.index(index) }.value()

}
