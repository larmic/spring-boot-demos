package de.larmic.testing

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest
import co.elastic.clients.elasticsearch.indices.ExistsRequest
import co.elastic.clients.elasticsearch.indices.RefreshRequest
import de.larmic.es.elasticsearch.TweetDocument

fun ElasticsearchClient.refreshIndex(): ElasticsearchClient {
    this.indices().refresh {r: RefreshRequest.Builder -> r.index(TweetDocument.documentIndex) }
    return this
}

fun ElasticsearchClient.deleteIndexIfExists(): ElasticsearchClient {
    if (this.indexExists(TweetDocument.documentIndex)) {
        this.indices().delete { d: DeleteIndexRequest.Builder -> d.index(TweetDocument.documentIndex) }
    }
    return this
}

fun ElasticsearchClient.createIndex(): ElasticsearchClient {
    this.indices().create { c: CreateIndexRequest.Builder -> c.index(TweetDocument.documentIndex) }
    return this
}

private fun ElasticsearchClient.indexExists(index: String) = this.indices().exists { e: ExistsRequest.Builder -> e.index(index) }.value()
