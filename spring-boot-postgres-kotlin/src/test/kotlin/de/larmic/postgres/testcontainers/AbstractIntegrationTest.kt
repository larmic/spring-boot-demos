package de.larmic.postgres.testcontainers

import de.larmic.postgres.database.TweetRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ActiveProfiles("it")
@ContextConfiguration(initializers = [PostgresContextInitializer::class])
abstract class AbstractIntegrationTest {

    @Autowired
    protected lateinit var tweetRepository: TweetRepository

    @BeforeEach
    fun setUp() {
        tweetRepository.deleteAll()
    }

}