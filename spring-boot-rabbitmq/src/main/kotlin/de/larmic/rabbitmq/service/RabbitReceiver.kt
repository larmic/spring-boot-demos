package de.larmic.rabbitmq.service

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitReceiver {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * This is for testing.
     */
    var lastMessage: Message? = null

    @RabbitListener(queues = ["\${larmic.amqp.queue-name}"])
    fun receiveMessage(message: Message) {
        log.info("[RABBIT] Received message ${String(message.body)}''")
        this.lastMessage = message
    }

}