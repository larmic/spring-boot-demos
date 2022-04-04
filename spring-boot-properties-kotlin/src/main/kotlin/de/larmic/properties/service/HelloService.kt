package de.larmic.properties.service

import de.larmic.properties.PropertiesApplication
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class HelloService(private val helloProperties: HelloProperties) {

    private val logger = LoggerFactory.getLogger(PropertiesApplication::class.java)

    fun logHelloProperties() {
        logger.info("Properties (hello.value): " + helloProperties.value)
    }
}