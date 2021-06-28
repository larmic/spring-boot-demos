package de.larmic.rest.rest;

import de.larmic.rest.rest.dto.TweetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {TweetController.class, InMemoryRepository.class})
class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryRepository inMemoryRepository;

    @BeforeEach
    void setUp() {
        inMemoryRepository.tweets.clear();
    }

    @Nested
    @DisplayName("tweet() with")
    class Tweet {

        @Test
        @DisplayName("body is empty")
        void bodyIsEmpty() throws Exception {
            mockMvc.perform(post("/").content(""))
                .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("body is not set")
        void bodyIsNull() throws Exception {
            mockMvc.perform(post("/"))
                .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("body is set")
        void bodyIsNotEmpty() throws Exception {
            mockMvc.perform(post("/").content("first test tweet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("first test tweet")))
                .andExpect(jsonPath("id").isNotEmpty());
        }
    }


    @Nested
    @DisplayName("readTweet() with")
    class ReadTweet {

        @Test
        @DisplayName("tweet not exists")
        void tweetNotExists() throws Exception {
            mockMvc.perform(get("/not-existing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
        }

        @Test
        @DisplayName("tweet exists")
        void tweetExists() throws Exception {
            final var tweet = addTweetToDatabase("second test tweet");

            mockMvc.perform(get("/"+tweet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(tweet.getId())))
                .andExpect(jsonPath("message", is(tweet.getMessage())));
        }
    }

    @Nested
    @DisplayName("readAllTweets() with")
    class ReadAllTweets {

        @Test
        @DisplayName("tweets are empty")
        void tweetsAreEmpty() throws Exception {
            mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
        }

        @Test
        @DisplayName("tweets are not empty")
        void tweetsAreNotEmpty() throws Exception {
            final var tweet = addTweetToDatabase("third test tweet");

            mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(tweet.getId())))
                .andExpect(jsonPath("$[0].message", is(tweet.getMessage())));
        }
    }

    @Nested
    @DisplayName("updateTweet() with")
    class UpdateTweet {

        @Test
        @DisplayName("tweet not exists")
        void tweetsAreEmpty() throws Exception {
            mockMvc.perform(put("/not-existing").content("tweet content changed"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
        }

        @Test
        @DisplayName("tweet exists")
        void tweetsAreNotEmpty() throws Exception {
            final var tweet = addTweetToDatabase("fourth test tweet");

            mockMvc.perform(put("/" + tweet.getId()).content("tweet content changed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(tweet.getId())))
                .andExpect(jsonPath("message", is("tweet content changed")));
        }
    }

    private TweetDto addTweetToDatabase(final String tweet) throws Exception {
        final var dto = new TweetDto(UUID.randomUUID().toString(), tweet);
        this.inMemoryRepository.tweets.put(dto.getId(), dto);
        return dto;
    }
}