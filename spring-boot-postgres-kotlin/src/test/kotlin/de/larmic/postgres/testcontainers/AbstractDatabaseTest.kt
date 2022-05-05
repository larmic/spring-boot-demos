package de.larmic.postgres.testcontainers

import de.larmic.postgres.database.TweetRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Transactional(propagation = Propagation.NEVER)
@DataJpaTest
@ActiveProfiles("database")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [PostgresContextInitializer::class])
abstract class AbstractDatabaseTest {

    @Autowired
    protected lateinit var tweetRepository: TweetRepository

    @BeforeEach
    fun setUp() {
        tweetRepository.deleteAll()
    }

}