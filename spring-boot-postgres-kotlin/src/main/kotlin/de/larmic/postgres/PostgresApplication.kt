package de.larmic.postgres

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PropertiesApplication

fun main(args: Array<String>) {
    runApplication<PropertiesApplication>(*args)
}