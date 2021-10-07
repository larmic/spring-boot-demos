package de.larmic.rabbitmq

import de.larmic.rabbitmq.properties.RabbitProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(value = [RabbitProperties::class])
class SpringBootRabbitMQApplication

fun main(args: Array<String>) {
    runApplication<SpringBootRabbitMQApplication>(*args)
}
