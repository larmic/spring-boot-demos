package de.larmic.testing

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy
import org.testcontainers.elasticsearch.ElasticsearchContainer


class ElasticsearchContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(context: ConfigurableApplicationContext) {
        elasticsearch.envMap.remove("xpack.security.enabled")

        elasticsearch.start()
        TestPropertyValues.of(
            "spring.elasticsearch.rest.uris=${elasticsearch.httpHostAddress}"
        ).applyTo(context.environment)
    }

    companion object {
        val elasticsearch = ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.5.1")
            .withExposedPorts(9200)
            .waitingFor(LogMessageWaitStrategy().withRegEx(".*\"message\":\"started\".*"))
    }
}