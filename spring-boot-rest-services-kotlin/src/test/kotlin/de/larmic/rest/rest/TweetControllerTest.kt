package de.larmic.rest.rest

import de.larmic.rest.rest.dto.TweetDto
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@WebMvcTest(value = [TweetController::class, InMemoryRepository::class])
internal class TweetControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var inMemoryRepository: InMemoryRepository

    @BeforeEach
    fun setUp() {
        inMemoryRepository.tweets.clear()
    }

    @Nested
    @DisplayName("tweet() with")
    internal inner class TweetTests {

        @Test
        fun `body is empty`() {
            mockMvc.post("/") {
                contentType = MediaType.APPLICATION_JSON
                content = ""
            }.andExpect {
                status { is4xxClientError() }
            }
        }

        @Test
        fun `body is not set`() {
            mockMvc.post("/") {
                contentType = MediaType.APPLICATION_JSON
                content = ""
            }.andExpect {
                status { is4xxClientError() }
            }
        }

        @Test
        fun `body is set`() {
            mockMvc.post("/") {
                contentType = MediaType.APPLICATION_JSON
                content = "first test tweet"
            }.andExpect {
                status { isOk() }
                content { jsonPath("$.message") { value("first test tweet") } }
                content { jsonPath("$.id") { exists() } }
            }
        }
    }

    @Nested
    @DisplayName("readTweet() with")
    internal inner class ReadTweetTests {

        @Test
        fun `tweet not exists`() {
            mockMvc.perform(get("/not-existing"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$").doesNotExist())
        }

        @Test
        fun `tweet exists`() {
            val tweet = "second test tweet".wrapInTweet().storeInDatabase()
            mockMvc.perform(get("/${tweet.id}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("id", `is`(tweet.id)))
                .andExpect(jsonPath("message", `is`(tweet.message)))
        }
    }

    @Nested
    @DisplayName("readAllTweets() with")
    internal inner class ReadAllTweetsTests {

        @Test
        fun `tweets are empty`() {
            mockMvc.perform(get("/"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$").isEmpty)
        }

        @Test
        fun `tweets are not empty`() {
            val tweet = "third test tweet".wrapInTweet().storeInDatabase()
            mockMvc.perform(get("/"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[0].id", `is`(tweet.id)))
                .andExpect(jsonPath("$[0].message", `is`(tweet.message)))
        }
    }

    @Nested
    @DisplayName("updateTweet() with")
    internal inner class UpdateTweetTests {

        @Test
        fun `tweet not exists`() {
            mockMvc.perform(put("/not-existing").content("tweet content changed"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$").doesNotExist())
        }

        @Test
        fun `tweet exists`() {
            val tweet = "fourth test tweet".wrapInTweet().storeInDatabase()
            mockMvc.perform(put("/" + tweet.id).content("tweet content changed"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("id", `is`(tweet.id)))
                .andExpect(jsonPath("message", `is`("tweet content changed")))
        }
    }

    private fun String.wrapInTweet() = TweetDto(UUID.randomUUID().toString(), this)

    private fun TweetDto.storeInDatabase(): TweetDto {
        inMemoryRepository.tweets[this.id] = this
        return this
    }
}