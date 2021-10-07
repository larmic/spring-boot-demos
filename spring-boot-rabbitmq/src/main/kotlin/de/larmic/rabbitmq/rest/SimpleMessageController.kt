package de.larmic.rabbitmq.rest

import de.larmic.rabbitmq.properties.RabbitProperties
import de.larmic.rabbitmq.service.RabbitSender
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class SimpleMessageController(private val rabbitSender: RabbitSender) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/")
    fun tweet(@RequestBody message: String) {
        log.info("[REST] Message '$message' received")
        rabbitSender.sendSimpleMessage(UUID.randomUUID(), message)
    }

}