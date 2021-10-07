package de.larmic.testcontainers.rest

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleMessageController() {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/")
    fun tweet(@RequestBody message: String) {
        log.info("[REST] Message '$message' received")
    }

}