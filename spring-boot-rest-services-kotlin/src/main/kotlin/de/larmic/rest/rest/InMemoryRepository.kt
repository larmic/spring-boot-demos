package de.larmic.rest.rest

import de.larmic.rest.rest.dto.TweetDto
import org.springframework.stereotype.Repository

@Repository
class InMemoryRepository {
    val tweets = mutableMapOf<String, TweetDto>()
}