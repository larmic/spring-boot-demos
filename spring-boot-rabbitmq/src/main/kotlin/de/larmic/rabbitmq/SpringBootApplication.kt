package de.larmic.rabbitmq

import de.larmic.rabbitmq.ampq.RabbitProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(value = [RabbitProperties::class])
open class SpringBootRabbitMQApplication

fun main(args: Array<String>) {
    runApplication<SpringBootRabbitMQApplication>(*args)
}
