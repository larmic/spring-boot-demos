package de.larmic.rabbitmq.elasticsearch

class TweetDocument(val message: String) {
    companion object {
        const val documentIndex = "twitter"
    }
}