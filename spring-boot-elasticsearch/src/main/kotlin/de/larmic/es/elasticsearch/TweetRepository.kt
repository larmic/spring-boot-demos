package de.larmic.es.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch.core.*
import de.larmic.es.elasticsearch.TweetDocument.Companion.documentIndex
import de.larmic.es.rest.Tweet
import org.springframework.stereotype.Service
import java.util.*


@Service
class TweetRepository(private val esClient: ElasticsearchClient) {

    fun storeTweet(tweet: String): String {
        val document = TweetDocument(tweet)

        val response = esClient.index { i: IndexRequest.Builder<Any> -> i.index(documentIndex).document(document) }

        return response.id()
    }

    fun getTweet(id: String): Tweet? {
        val response: GetResponse<TweetDocument> = esClient.get(
            { g: GetRequest.Builder -> g.index(documentIndex).id(id) },
            TweetDocument::class.java
        )

        return if (response.found()) Tweet(response.id(), response.source()!!.message) else null
    }

    fun getAllTweets(): List<Tweet> {
        val response: SearchResponse<TweetDocument> = esClient.search(
            { s: SearchRequest.Builder -> s.index(documentIndex) },
            TweetDocument::class.java
        )

        return response.hits().hits().map { Tweet(it.id(), it.source()!!.message) }
    }

    fun updateTweet(id: String, tweet: String): RestStatus {
        if (!tweetExists(id)) {
            return RestStatus.NOT_FOUND
        }

        val jsonMap = HashMap<String, Any>()
        jsonMap["message"] = tweet

        val response: UpdateResponse<TweetDocument> = esClient.update(
            { u: UpdateRequest.Builder<TweetDocument, Any> -> u.index(documentIndex).id(id).doc(jsonMap) },
            TweetDocument::class.java
        )

        // TODO response.result()

        return RestStatus.OK
    }

    fun deleteTweet(id: String): RestStatus {
        if (!tweetExists(id)) {
            return RestStatus.NOT_FOUND
        }

        val response = esClient.delete { d: DeleteRequest.Builder -> d.index(documentIndex).id(id) }

        // TODO response.result()

        return RestStatus.OK
    }

    private fun tweetExists(id: String): Boolean {
        // TODO use ExistResponse
        val response: GetResponse<TweetDocument> = esClient.get(
            { g: GetRequest.Builder -> g.index(documentIndex).id(id) },
            TweetDocument::class.java
        )

        return response.found()
    }

    enum class RestStatus {
        OK, NOT_FOUND
    }
}