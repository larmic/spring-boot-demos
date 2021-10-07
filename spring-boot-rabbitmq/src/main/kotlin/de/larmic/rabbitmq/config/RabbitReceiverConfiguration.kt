package de.larmic.rabbitmq.config

import de.larmic.rabbitmq.properties.RabbitProperties
import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Bind rabbit exchange, inbound queue and inbound routing key to enable @RabbitListener.
 * This class is not required to send messages!
 */
@Configuration
open class RabbitReceiverConfiguration(private val rabbitProperties: RabbitProperties) {

    @Bean
    open fun ampqExchange(): DirectExchange {
        return ExchangeBuilder.directExchange(rabbitProperties.exchangeName).build() as DirectExchange
    }

    @Bean
    open fun simpleMessageQueueName(): Queue {
        return QueueBuilder.durable(rabbitProperties.queueName)
            .withArgument("x-dead-letter-exchange", rabbitProperties.exchangeName)
            .build()
    }

    @Bean
    open fun simpleMessageAmqpBinding(simpleMessageQueueName: Queue, ampqExchange: DirectExchange): Binding {
        return BindingBuilder.bind(simpleMessageQueueName).to(ampqExchange).with(rabbitProperties.routingKey)
    }
}