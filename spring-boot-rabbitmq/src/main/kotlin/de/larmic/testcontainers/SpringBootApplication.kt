package de.larmic.testcontainers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SpringBootRabbitMQApplication

fun main(args: Array<String>) {
    runApplication<SpringBootRabbitMQApplication>(*args)
}
