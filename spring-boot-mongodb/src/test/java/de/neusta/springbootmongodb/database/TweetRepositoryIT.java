package de.neusta.springbootmongodb.database;

import de.neusta.springbootmongodb.database.model.TweetEntity;
import de.neusta.springbootmongodb.testcontainers.MongoDbTestSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TweetRepositoryIT extends MongoDbTestSetup {

    @Autowired
    private TweetRepository tweetRepository;

    @Test
    void saveTweetEntityToDb() {
        TweetEntity tweetEntity = new TweetEntity("testmessage");
        UUID id = tweetEntity.getId();

        tweetRepository.save(tweetEntity);

        assertTrue(tweetRepository.findById(id).isPresent());

        TweetEntity retrievedTweetEntity = tweetRepository.findById(id).get();

        assertThat(retrievedTweetEntity.getCreateDate())
                .isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
        assertThat(retrievedTweetEntity.getLastUpdateDate())
                .isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
        assertThat(retrievedTweetEntity.getMessage()).isEqualTo("testmessage");
    }
}
