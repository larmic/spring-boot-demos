package de.larmic.properties

import de.larmic.properties.service.HelloProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(value = [HelloProperties::class])
open class PropertiesApplication

fun main(args: Array<String>) {
    runApplication<PropertiesApplication>(*args)
}