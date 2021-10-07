package de.larmic.rabbitmq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SpringBootElasticsearchOverHttpApplication

fun main(args: Array<String>) {
    runApplication<SpringBootElasticsearchOverHttpApplication>(*args)
}
