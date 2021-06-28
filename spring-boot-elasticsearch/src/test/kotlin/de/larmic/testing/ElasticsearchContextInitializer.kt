package de.larmic.testing

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.elasticsearch.ElasticsearchContainer

class ElasticsearchContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(context: ConfigurableApplicationContext) {
        elasticsearch.start()
        TestPropertyValues.of(
                "spring.elasticsearch.rest.uris=${elasticsearch.httpHostAddress}"
        ).applyTo(context.environment)
    }

    companion object {
        val elasticsearch = ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch-oss:7.10.1")
    }
}