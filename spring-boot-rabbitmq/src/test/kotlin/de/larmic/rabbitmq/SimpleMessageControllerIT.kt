package de.larmic.rabbitmq

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
//@ContextConfiguration(initializers = [ElasticsearchContextInitializer::class])
class SimpleMessageControllerIT {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `post a simple message`() {
        val response = testRestTemplate.exchange("/", HttpMethod.POST, HttpEntity("simple tweet"), String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        // TODO
    }

}