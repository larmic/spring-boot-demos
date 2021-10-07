package de.larmic.rabbitmq.ampq

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class RabbitMQConfig(private val rabbitProperties: RabbitProperties) {

    @Bean
    open fun ampqExchange(): DirectExchange {
        return ExchangeBuilder.directExchange(rabbitProperties.exchangeName).build() as DirectExchange
    }

    @Bean
    open fun simpleMessageQueueName(): Queue {
        return QueueBuilder.durable(rabbitProperties.inbound.queueName)
            .withArgument("x-dead-letter-exchange", rabbitProperties.exchangeName)
            .build()
    }

    @Bean
    open fun simpleMessageAmqpBinding(simpleMessageQueueName: Queue, ampqExchange: DirectExchange): Binding {
        return BindingBuilder.bind(simpleMessageQueueName).to(ampqExchange).with(rabbitProperties.inbound.routingKey)
    }
}