package de.larmic.rabbitmq.service

import de.larmic.rabbitmq.properties.RabbitProperties
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RabbitSender(
    private val rabbitTemplate: RabbitTemplate,
    private val rabbitProperties: RabbitProperties
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun sendSimpleMessage(message: String) {
        log.info("[RABBIT] Send message $message''")

        rabbitTemplate.send(rabbitProperties.exchangeName, rabbitProperties.routingKey, Message(message.toByteArray()))
    }

}