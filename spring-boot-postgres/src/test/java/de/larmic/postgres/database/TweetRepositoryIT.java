package de.larmic.postgres.database;

import de.larmic.postgres.database.model.TweetEntity;
import de.larmic.postgres.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class TweetRepositoryIT extends AbstractIntegrationTest {

    @Test
    void save() {
        final var entity = new TweetEntity("Some test message");

        this.tweetRepository.save(entity);

        assertThat(entity.getId()).isNotZero();
        assertThat(entity.getMessage()).isEqualTo("Some test message");
        assertThat(entity.getCreateDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
        assertThat(entity.getLastUpdateDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }
}