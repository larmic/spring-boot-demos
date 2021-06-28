package de.larmic.testcontainers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SpringBootElasticsearchOverHttpApplication

fun main(args: Array<String>) {
    runApplication<SpringBootElasticsearchOverHttpApplication>(*args)
}
