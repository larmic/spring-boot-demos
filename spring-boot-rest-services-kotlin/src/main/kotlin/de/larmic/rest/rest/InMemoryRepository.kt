package de.larmic.rest.rest

import de.larmic.rest.rest.dto.TweetDto
import org.springframework.stereotype.Repository

@Repository
class InMemoryRepository(val tweets: MutableMap<String, TweetDto> = mutableMapOf()) {

    fun exists(id: String) = tweets.containsKey(id)

}