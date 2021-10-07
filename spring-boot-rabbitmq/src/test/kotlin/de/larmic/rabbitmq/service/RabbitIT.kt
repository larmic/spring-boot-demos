package de.larmic.rabbitmq.service

import de.larmic.rabbitmq.testcontainers.RabbitMQContextInitializer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ActiveProfiles("it")
@ContextConfiguration(initializers = [RabbitMQContextInitializer::class])
class RabbitIT {

    @Autowired
    private lateinit var rabbitSender: RabbitSender

    @Test
    fun `send message`() {
        rabbitSender.sendSimpleMessage("demo message")
    }

}