package de.larmic.postgres.database

import de.larmic.postgres.database.model.TweetEntity
import de.larmic.postgres.testcontainers.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

internal class TweetRepositoryIT : AbstractIntegrationTest() {

    @Test
    fun save() {
        val entity = TweetEntity(message = "Some test message")

        tweetRepository.save(entity)

        assertThat(entity.id).isNotZero
        assertThat(entity.message).isEqualTo("Some test message")
        assertThat(entity.createDate).isCloseTo(LocalDateTime.now(), Assertions.within(1, ChronoUnit.SECONDS))
        assertThat(entity.lastUpdateDate).isCloseTo(LocalDateTime.now(), Assertions.within(1, ChronoUnit.SECONDS))
    }
}