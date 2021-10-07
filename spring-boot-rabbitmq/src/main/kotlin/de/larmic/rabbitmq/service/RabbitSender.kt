package de.larmic.rabbitmq.service

import de.larmic.rabbitmq.properties.RabbitProperties
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class RabbitSender(
    private val rabbitTemplate: RabbitTemplate,
    private val rabbitProperties: RabbitProperties
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun sendSimpleMessage(messageId: UUID, text: String) {
        log.info("[RABBIT] Send message $text''")

        rabbitTemplate.convertAndSend(rabbitProperties.exchangeName, rabbitProperties.routingKey, text.toJsonRabbitMessage()) {
            it.messageProperties.headers["message-id"] = messageId.toString()
            it.messageProperties.contentType = MessageProperties.CONTENT_TYPE_JSON
            it.messageProperties.contentEncoding = "UTF-8"
            it
        }
    }

    private fun String.toJsonRabbitMessage() = Message("{message: $this}".toByteArray())
}