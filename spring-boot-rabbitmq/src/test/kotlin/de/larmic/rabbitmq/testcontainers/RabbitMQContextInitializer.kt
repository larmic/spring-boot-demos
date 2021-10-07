package de.larmic.rabbitmq.testcontainers

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait

class RabbitMQContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        rabbitMqContainer.start()

        TestPropertyValues.of("spring.rabbitmq.port=${rabbitMqContainer.getMappedPort(5672)}")
                .applyTo(configurableApplicationContext.environment)
    }

    companion object {
        private val rabbitMqContainer = KGenericContainer("rabbitmq:3-management")
                .withExposedPorts(5672)
                .withEnv("RABBITMQ_DEFAULT_USER", "it-rabbit-user")
                .withEnv("RABBITMQ_DEFAULT_PASS", "it-rabbit-password")
                .waitingFor(Wait.forListeningPort())
    }
}

class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)