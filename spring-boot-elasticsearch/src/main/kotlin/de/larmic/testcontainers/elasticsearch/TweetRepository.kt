package de.larmic.testcontainers.elasticsearch

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.larmic.testcontainers.elasticsearch.TweetDocument.Companion.documentIndex
import de.larmic.testcontainers.rest.Tweet
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.rest.RestStatus
import org.elasticsearch.search.fetch.subphase.FetchSourceContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class TweetRepository(private val restHighLevelClient: RestHighLevelClient) {

    private val mapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(KotlinModule())

    fun storeTweet(tweet: String): String {
        val document = TweetDocument(tweet)
        val writeValueAsString = mapper.writeValueAsString(document)
        val request = IndexRequest(documentIndex)
        request.source(writeValueAsString, XContentType.JSON)
        return restHighLevelClient.index(request, RequestOptions.DEFAULT).id
    }

    fun getTweet(id: String): Tweet? {
        val getRequest = GetRequest(documentIndex, id)
        val getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT)
        return when (getResponse.isExists) {
            true -> {
                val sourceAsString = getResponse.sourceAsString
                val tweetDocument = mapper.readValue(sourceAsString, TweetDocument::class.java)
                return Tweet(getResponse.id, tweetDocument.message)
            }
            false -> null
        }
    }

    fun getAllTweets(): List<Tweet> {
        // see https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.x/java-rest-high-search.html
        val searchRequest = SearchRequest(documentIndex)
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT).hits
                .map { Tweet(it.id, mapper.readValue(it.sourceAsString, TweetDocument::class.java).message) }
    }

    fun updateTweet(id: String, tweet: String): RestStatus {
        if (!tweetExists(id)) {
            return RestStatus.NOT_FOUND
        }

        val jsonMap = HashMap<String, Any>()
        jsonMap["message"] = tweet
        val request = UpdateRequest(documentIndex, id).doc(jsonMap, XContentType.JSON)
        val updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT)
        return updateResponse.status()
    }

    fun deleteTweet(id: String): RestStatus {
        val request = DeleteRequest(documentIndex, id)
        val deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT)
        return deleteResponse.status()
    }

    private fun tweetExists(id: String): Boolean {
        val getRequest = GetRequest(documentIndex, id)
        getRequest.fetchSourceContext(FetchSourceContext(false))
        getRequest.storedFields("_none_")
        return restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT)
    }
}