package de.larmic.es.elasticsearch

class TweetDocument(val message: String) {
    companion object {
        const val documentIndex = "twitter"
    }
}