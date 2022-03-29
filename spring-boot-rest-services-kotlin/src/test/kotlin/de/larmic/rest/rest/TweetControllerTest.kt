package de.larmic.rest.rest

import de.larmic.rest.rest.dto.TweetDto
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@WebMvcTest(value = [TweetController::class, InMemoryRepository::class])
internal class TweetControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private val inMemoryRepository: InMemoryRepository? = null

    @BeforeEach
    fun setUp() {
        inMemoryRepository!!.tweets.clear()
    }

    @Nested
    @DisplayName("tweet() with")
    internal inner class TweetTests {
        @Test
        @DisplayName("body is empty")
        fun bodyIsEmpty() {
            mockMvc.perform(MockMvcRequestBuilders.post("/").content(""))
                .andExpect(status().is4xxClientError)
        }

        @Test
        @DisplayName("body is not set")
        fun bodyIsNull() {
            mockMvc.perform(MockMvcRequestBuilders.post("/"))
                .andExpect(status().is4xxClientError)
        }

        @Test
        @DisplayName("body is set")
        fun bodyIsNotEmpty() {
            mockMvc.perform(MockMvcRequestBuilders.post("/").content("first test tweet"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("message", `is`("first test tweet")))
                .andExpect(jsonPath("id").isNotEmpty)
        }
    }

    @Nested
    @DisplayName("readTweet() with")
    internal inner class ReadTweetTests {
        @Test
        @DisplayName("tweet not exists")
        fun tweetNotExists() {
            mockMvc.perform(MockMvcRequestBuilders.get("/not-existing"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$").doesNotExist())
        }

        @Test
        @DisplayName("tweet exists")
        fun tweetExists() {
            val tweet = addTweetToDatabase("second test tweet")
            mockMvc.perform(MockMvcRequestBuilders.get("/" + tweet.id))
                .andExpect(status().isOk)
                .andExpect(jsonPath("id", `is`(tweet.id)))
                .andExpect(jsonPath("message", `is`(tweet.message)))
        }
    }

    @Nested
    @DisplayName("readAllTweets() with")
    internal inner class ReadAllTweetsTests {
        @Test
        @DisplayName("tweets are empty")
        fun tweetsAreEmpty() {
            mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$").isEmpty)
        }

        @Test
        @DisplayName("tweets are not empty")
        fun tweetsAreNotEmpty() {
            val tweet = addTweetToDatabase("third test tweet")
            mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[0].id", `is`<String>(tweet.id)))
                .andExpect(jsonPath("$[0].message", `is`<String>(tweet.message)))
        }
    }

    @Nested
    @DisplayName("updateTweet() with")
    internal inner class UpdateTweetTests {
        @Test
        @DisplayName("tweet not exists")
        fun tweetsAreEmpty() {
            mockMvc.perform(MockMvcRequestBuilders.put("/not-existing").content("tweet content changed"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$").doesNotExist())
        }

        @Test
        @DisplayName("tweet exists")
        fun tweetsAreNotEmpty() {
            val tweet = addTweetToDatabase("fourth test tweet")
            mockMvc.perform(MockMvcRequestBuilders.put("/" + tweet.id).content("tweet content changed"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("id", `is`<String>(tweet.id)))
                .andExpect(jsonPath("message", `is`("tweet content changed")))
        }
    }

    private fun addTweetToDatabase(tweet: String): TweetDto {
        val dto = TweetDto(UUID.randomUUID().toString(), tweet)
        inMemoryRepository!!.tweets[dto.id] = dto
        return dto
    }
}