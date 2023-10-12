package de.larmic.postgres.testcontainers

import de.larmic.postgres.database.TweetRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@ActiveProfiles("it")
abstract class AbstractIntegrationTest {

    @Autowired
    protected lateinit var tweetRepository: TweetRepository

    @BeforeEach
    fun setUp() {
        tweetRepository.deleteAll()
    }

    companion object {
        @Container
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:16.0-alpine")
    }
}