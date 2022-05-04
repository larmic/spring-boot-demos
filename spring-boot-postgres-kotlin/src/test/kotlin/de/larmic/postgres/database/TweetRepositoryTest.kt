package de.larmic.postgres.database

import de.larmic.postgres.database.model.TweetEntity
import de.larmic.postgres.testcontainers.AbstractDatabaseTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class TweetRepositoryTest : AbstractDatabaseTest() {

    @Test
    fun save() {
        val entity = TweetEntity()
        entity.message = "Some test message"

        tweetRepository.save(entity)
        Assertions.assertThat(entity.id).isNotZero
        Assertions.assertThat(entity.message).isEqualTo("Some test message")
        Assertions.assertThat(entity.createDate).isCloseTo(LocalDateTime.now(), Assertions.within(1, ChronoUnit.SECONDS))
        Assertions.assertThat(entity.lastUpdateDate).isCloseTo(LocalDateTime.now(), Assertions.within(1, ChronoUnit.SECONDS))
    }
}