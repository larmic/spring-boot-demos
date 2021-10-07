package de.larmic.rabbitmq.ampq

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated

@ConstructorBinding
@ConfigurationProperties("larmic.amqp")
@Validated
class RabbitProperties(val exchangeName: String, val inbound: Inbound, val outbound: Outbound) {

    data class Inbound(val queueName: String, val routingKey: String)
    data class Outbound(val routingKey: String)

}