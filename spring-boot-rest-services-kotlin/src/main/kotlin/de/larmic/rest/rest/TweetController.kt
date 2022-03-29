package de.larmic.rest.rest

import de.larmic.rest.rest.dto.TweetDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TweetController(private val inMemoryRepository: InMemoryRepository) {

    @PostMapping("/")
    fun tweet(@RequestBody(required = true) message: String): TweetDto {
        val dto = TweetDto(message = message)
        inMemoryRepository.tweets[dto.id] = dto
        return dto
    }

    @GetMapping("/{id}")
    fun readTweet(@PathVariable id: String): ResponseEntity<TweetDto?> = if (inMemoryRepository.tweets.containsKey(id)) {
        ResponseEntity.ok(inMemoryRepository.tweets[id])
    } else ResponseEntity.notFound().build()

    @GetMapping("/")
    fun readAllTweets() = inMemoryRepository.tweets.values

    @PutMapping("/{id}")
    fun updateTweet(@PathVariable id: String, @RequestBody message: String): ResponseEntity<TweetDto> {
        if (inMemoryRepository.tweets.containsKey(id)) {
            val dto = TweetDto(id, message)
            inMemoryRepository.tweets[id] = dto
            return ResponseEntity.ok(dto)
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
}