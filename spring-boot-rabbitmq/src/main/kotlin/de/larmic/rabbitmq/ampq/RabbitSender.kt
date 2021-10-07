package de.larmic.rabbitmq.ampq

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

        rabbitTemplate.send(rabbitProperties.exchangeName, rabbitProperties.outbound.routingKey, Message(message.toByteArray()))
    }

}