package de.larmic.rest.rest

import de.larmic.rest.rest.dto.TweetDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class TweetController(private val inMemoryRepository: InMemoryRepository) {

    @PostMapping("/")
    fun tweet(@RequestBody(required = true) message: String) = message.wrapInTweet().storeInDatabase()

    @GetMapping("/{id}")
    fun readTweet(@PathVariable id: String): ResponseEntity<TweetDto?> = if (inMemoryRepository.tweets.containsKey(id)) {
        ResponseEntity.ok(inMemoryRepository.tweets[id])
    } else ResponseEntity.notFound().build()

    @GetMapping("/")
    fun readAllTweets() = inMemoryRepository.tweets.values

    @PutMapping("/{id}")
    fun updateTweet(@PathVariable id: String, @RequestBody message: String): ResponseEntity<TweetDto> {
        if (inMemoryRepository.tweets.containsKey(id)) {
            return message.wrapInTweet().storeInDatabase().wrapInResponse()
        }
        return ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteTweet(@PathVariable id: String): ResponseEntity<*> {
        if (inMemoryRepository.tweets.containsKey(id)) {
            inMemoryRepository.tweets.remove(id)
            return ResponseEntity.ok().build<Any>()
        }
        return ResponseEntity.notFound().build<Any>()
    }

    private fun String.wrapInTweet() = TweetDto(UUID.randomUUID().toString(), this)

    private fun TweetDto.storeInDatabase(): TweetDto {
        inMemoryRepository.tweets[this.id] = this
        return this
    }

    private fun TweetDto.wrapInResponse() = ResponseEntity.ok(this)
}