package de.larmic.springbootmongodb.endtoend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.larmic.springbootmongodb.database.TweetRepository;
import de.larmic.springbootmongodb.rest.dto.TweetDto;
import de.larmic.springbootmongodb.testcontainers.MongoDbTestSetup;
import de.larmic.springbootmongodb.database.model.TweetEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TweetControllerIT extends MongoDbTestSetup {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TweetRepository tweetRepository;

    @Test
    void tweet() throws Exception {
        MockMvc mock = MockMvcBuilders.webAppContextSetup(context).build();
        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mock.perform(post("/").content("testtweet"))
                .andExpect(status().isOk())
                .andReturn();

        TweetDto tweetDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TweetDto.class);

        assertThat(tweetDto.getMessage()).isEqualTo("testtweet");

        assertTrue(tweetRepository.existsById(UUID.fromString(tweetDto.getId())));
    }

    @Test
    void readTweet() throws Exception {
        MockMvc mock = MockMvcBuilders.webAppContextSetup(context).build();
        ObjectMapper objectMapper = new ObjectMapper();

        TweetEntity tweetEntity = new TweetEntity("testtweet");
        tweetRepository.save(tweetEntity);

        MvcResult mvcResult = mock.perform(get("/" + tweetEntity.getId()))
                .andExpect(status().isOk())
                .andReturn();

        TweetDto tweetDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TweetDto.class);

        assertThat(tweetDto.getMessage()).isEqualTo("testtweet");
    }

    @Test
    void readTweetWithoutResult() throws Exception {
        MockMvc mock = MockMvcBuilders.webAppContextSetup(context).build();

        mock.perform(get("/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void readAllTweets() throws Exception {
        MockMvc mock = MockMvcBuilders.webAppContextSetup(context).build();
        ObjectMapper objectMapper = new ObjectMapper();

        TweetEntity tweetEntity1 = new TweetEntity("testtweet1");
        tweetRepository.save(tweetEntity1);
        TweetEntity tweetEntity2 = new TweetEntity("testtweet2");
        tweetRepository.save(tweetEntity2);

        MvcResult mvcResult = mock.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        List<TweetDto> tweetDtoList =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(tweetDtoList).hasSize(2);
        assertThat(tweetDtoList.get(0).getId()).isEqualTo(tweetEntity1.getId().toString());
        assertThat(tweetDtoList.get(1).getId()).isEqualTo(tweetEntity2.getId().toString());
        assertThat(tweetDtoList.get(0).getMessage()).isEqualTo("testtweet1");
        assertThat(tweetDtoList.get(1).getMessage()).isEqualTo("testtweet2");
    }

    @Test
    void updateTweet() throws Exception {
        MockMvc mock = MockMvcBuilders.webAppContextSetup(context).build();
        ObjectMapper objectMapper = new ObjectMapper();

        TweetEntity tweetEntity = new TweetEntity("testtweet");
        tweetRepository.save(tweetEntity);

        MvcResult mvcResult = mock.perform(put("/" + tweetEntity.getId())
                        .content("updatedtesttweet"))
                .andExpect(status().isOk())
                .andReturn();

        TweetDto tweetDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TweetDto.class);

        assertThat(tweetDto.getMessage()).isEqualTo("updatedtesttweet");

        Optional<TweetEntity> updatedTweetEntity = tweetRepository.findById(tweetEntity.getId());
        assertThat(updatedTweetEntity).isPresent();
        assertThat(updatedTweetEntity.get().getMessage()).isEqualTo("updatedtesttweet");
        assertThat(updatedTweetEntity.get().getLastUpdateDate()).isNotEqualTo(updatedTweetEntity.get().getCreateDate());
    }

    @Test
    void updateTweetWithoutResult() throws Exception {
        MockMvc mock = MockMvcBuilders.webAppContextSetup(context).build();

        mock.perform(put("/" + UUID.randomUUID())
                        .content("updatedtesttweet"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTweet() throws Exception {
        MockMvc mock = MockMvcBuilders.webAppContextSetup(context).build();

        TweetEntity tweetEntity = new TweetEntity("testtweet");
        tweetRepository.save(tweetEntity);

        assertThat(tweetRepository.findById(tweetEntity.getId())).isPresent();

        mock.perform(delete("/" + tweetEntity.getId()))
                .andExpect(status().isOk());

        assertThat(tweetRepository.findById(tweetEntity.getId())).isNotPresent();
    }

    @Test
    void deleteTweetWithoutResult() throws Exception {
        MockMvc mock = MockMvcBuilders.webAppContextSetup(context).build();

        mock.perform(delete("/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
