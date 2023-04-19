package de.larmic.es.elasticsearch

import com.fasterxml.jackson.annotation.JsonProperty

class TweetDocument(@JsonProperty("message") val message: String) {
    companion object {
        const val documentIndex = "twitter"
    }
}