package de.larmic.postgres.rest

import de.larmic.postgres.database.TweetRepository
import de.larmic.postgres.database.model.TweetEntity
import de.larmic.postgres.rest.dto.TweetDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class TweetController(private val tweetRepository: TweetRepository) {

    @PostMapping("/")
    fun tweet(@RequestBody message: String): TweetDto {
        val tweetEntity = TweetEntity(message = message)
        val entity: TweetEntity = tweetRepository.save(tweetEntity)
        return mapToDto(entity)
    }

    @GetMapping("/{id}")
    fun readTweet(@PathVariable id: String?): ResponseEntity<TweetDto> {
        if (tweetRepository.existsById(java.lang.Long.valueOf(id))) {
            val entity = tweetRepository.getById(java.lang.Long.valueOf(id))!!
            return ResponseEntity.ok(mapToDto(entity))
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/")
    fun readAllTweets(): Collection<TweetDto> {
        return tweetRepository.findAll().map { mapToDto(it!!) }
    }

    @PutMapping("/{id}")
    fun updateTweet(@PathVariable id: String, @RequestBody message: String): ResponseEntity<TweetDto> {
        if (tweetRepository.existsById(java.lang.Long.valueOf(id))) {
            val entity = tweetRepository.getById(java.lang.Long.valueOf(id))!!

            entity.message = message
            entity.lastUpdateDate = LocalDateTime.now()

            tweetRepository.save(entity)
            return ResponseEntity.ok(mapToDto(entity))
        }
        return ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteTweet(@PathVariable id: String?): ResponseEntity<*> {
        if (tweetRepository.existsById(java.lang.Long.valueOf(id))) {
            tweetRepository.deleteById(java.lang.Long.valueOf(id))
            return ResponseEntity.ok().build<Any>()
        }
        return ResponseEntity.notFound().build<Any>()
    }

    private fun mapToDto(entity: TweetEntity): TweetDto {
        return TweetDto(entity.id.toString(), entity.message)
    }
}