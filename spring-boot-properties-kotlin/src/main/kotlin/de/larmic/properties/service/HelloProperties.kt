package de.larmic.properties.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "hello")
@ConstructorBinding
class HelloProperties(val value: String)