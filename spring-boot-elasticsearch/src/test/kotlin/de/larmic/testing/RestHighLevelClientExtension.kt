package de.larmic.testing

import de.larmic.testcontainers.elasticsearch.TweetDocument
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.client.indices.GetIndexRequest

fun RestHighLevelClient.refreshIndex(): RestHighLevelClient {
    val request = RefreshRequest(TweetDocument.documentIndex)
    this.indices().refresh(request, RequestOptions.DEFAULT)
    return this
}

fun RestHighLevelClient.deleteIndexIfExists(): RestHighLevelClient {
    if (this.indexExists(TweetDocument.documentIndex)) {
        this.indices().delete(DeleteIndexRequest(TweetDocument.documentIndex), RequestOptions.DEFAULT)
    }
    return this
}

fun RestHighLevelClient.createIndex(): RestHighLevelClient {
    this.indices().create(CreateIndexRequest(TweetDocument.documentIndex), RequestOptions.DEFAULT)
    return this
}

private fun RestHighLevelClient.indexExists(index: String) = this.indices().exists(GetIndexRequest(index), RequestOptions.DEFAULT)
