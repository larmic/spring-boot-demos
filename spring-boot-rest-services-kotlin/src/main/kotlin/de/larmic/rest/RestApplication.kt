package de.larmic.rest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class RestApplication

fun main(args: Array<String>) {
    runApplication<RestApplication>(*args)
}