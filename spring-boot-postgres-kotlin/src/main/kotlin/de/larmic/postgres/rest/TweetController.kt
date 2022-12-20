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
    fun tweet(@RequestBody message: String) = tweetRepository.save(TweetEntity(message = message)).mapToDto()

    @GetMapping("/{id}")
    fun readTweet(@PathVariable id: String?): ResponseEntity<TweetDto> {
        if (tweetRepository.existsById(java.lang.Long.valueOf(id))) {
            val entity = tweetRepository.getReferenceById(java.lang.Long.valueOf(id))!!
            return entity.wrapInResponse()
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/")
    fun readAllTweets() = tweetRepository.findAll().map { it!!.mapToDto() }

    @PutMapping("/{id}")
    fun updateTweet(@PathVariable id: String, @RequestBody message: String): ResponseEntity<TweetDto> {
        if (tweetRepository.existsById(java.lang.Long.valueOf(id))) {
            val entity = tweetRepository.getReferenceById(java.lang.Long.valueOf(id))!!

            entity.message = message
            entity.lastUpdateDate = LocalDateTime.now()

            tweetRepository.save(entity)

            return entity.wrapInResponse()
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

    private fun TweetEntity.mapToDto() = TweetDto(this.id.toString(), this.message)
    private fun TweetEntity.wrapInResponse() = ResponseEntity.ok(this.mapToDto())
}