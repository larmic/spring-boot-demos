package de.larmic.rabbitmq.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties("larmic.amqp")
@Validated
class RabbitProperties(val exchangeName: String, val queueName: String, val routingKey: String)