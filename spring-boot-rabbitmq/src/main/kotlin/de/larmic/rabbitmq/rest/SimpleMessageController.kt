package de.larmic.rabbitmq.rest

import de.larmic.rabbitmq.ampq.RabbitProperties
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleMessageController(private val rabbitProperties: RabbitProperties) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/")
    fun tweet(@RequestBody message: String) {
        log.info("rabbitProperties.exchangeName '${rabbitProperties.exchangeName}'")
        log.info("rabbitProperties.inbound.queueName '${rabbitProperties.inbound.queueName}'")
        log.info("rabbitProperties.inbound.routingKey '${rabbitProperties.inbound.routingKey}'")
        log.info("rabbitProperties.outbound.routingKey '${rabbitProperties.outbound.routingKey}'")
        log.info("[REST] Message '$message' received")
    }

}