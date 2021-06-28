package de.larmic.testcontainers.rest

import de.larmic.testcontainers.elasticsearch.TweetRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TweetController(private val tweetRepository: TweetRepository) {

    @PostMapping("/")
    fun tweet(@RequestBody tweet: String) = tweetRepository.storeTweet(tweet)

    @GetMapping("/{id}")
    fun readTweet(@PathVariable id: String) = tweetRepository.getTweet(id).mapToResponse()

    @GetMapping("/")
    fun readAllTweets() = tweetRepository.getAllTweets()

    @PutMapping("/{id}")
    fun updateTweet(@PathVariable id: String, @RequestBody tweet: String) = tweetRepository.updateTweet(id, tweet)

    @DeleteMapping("/{id}")
    fun deleteTweet(@PathVariable id: String) = tweetRepository.deleteTweet(id)

    private fun Tweet?.mapToResponse() = if (this != null) ResponseEntity(this, HttpStatus.OK) else ResponseEntity.notFound().build()

}