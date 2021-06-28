package de.larmic.testcontainers.elasticsearch

class TweetDocument(val message: String) {
    companion object {
        const val documentIndex = "twitter"
    }
}