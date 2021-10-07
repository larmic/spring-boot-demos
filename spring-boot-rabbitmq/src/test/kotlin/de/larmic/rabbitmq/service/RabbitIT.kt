package de.larmic.rabbitmq.service

import de.larmic.rabbitmq.testcontainers.RabbitMQContextInitializer
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.MessageProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import java.util.*

@SpringBootTest
@ActiveProfiles("it")
@ContextConfiguration(initializers = [RabbitMQContextInitializer::class])
class RabbitIT {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var rabbitSender: RabbitSender

    @Autowired
    private lateinit var rabbitReceiver: RabbitReceiver

    @Test
    fun `send message`() {
        val messageId = UUID.randomUUID()
        val text = "demo message"
        rabbitSender.sendSimpleMessage(messageId = messageId, text = text)

        val receivedMessage = waitForRabbitResponse()

        assertThat(receivedMessage.messageProperties.messageId).isEqualTo(messageId.toString())
        assertThat(receivedMessage.messageProperties.contentType).isEqualTo(MessageProperties.CONTENT_TYPE_JSON)
        assertThat(receivedMessage.messageProperties.contentEncoding).isEqualTo("UTF-8")
        assertThat(receivedMessage.messageProperties.headers["custom-header"]).isEqualTo("hi there!")
        assertThat(String(receivedMessage.body)).isEqualTo("{message: $text}")
    }

    private fun waitForRabbitResponse() = runBlocking {
        withTimeout(30000) {
            while (rabbitReceiver.lastMessage == null) {
                log.info("Message not received. Waiting for 200 ms.")
                delay(200)
            }
            rabbitReceiver.lastMessage!!
        }
    }
}