package de.larmic.testing

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.elasticsearch.ElasticsearchContainer
import java.time.Duration
import java.time.temporal.ChronoUnit


class ElasticsearchContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(context: ConfigurableApplicationContext) {
        // custom wait strategy for tls and basic auth
        //elasticsearch.setWaitStrategy(
        //    HttpWaitStrategy()
        //        .forPort(9200)
        //        //.usingTls()
        //        .forStatusCode(HTTP_OK)
        //        .forStatusCode(HTTP_UNAUTHORIZED)
        //        .withBasicCredentials("elastic", "secr3t")
        //        .withStartupTimeout(Duration.ofMinutes(1))
        //)

        elasticsearch.start()

        TestPropertyValues.of(
            "spring.elasticsearch.uris=${elasticsearch.httpHostAddress}"
        ).applyTo(context.environment)
    }

    companion object {
        // at this time there is no open source edition of elasticsearch 8.x
        // so disable security stuff
        val elasticsearch = ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.5.3")
            .withExposedPorts(9200)
            .withPassword("secr3t")
            .withEnv("xpack.security.enabled", "false")
            .withEnv("xpack.security.transport.ssl.enabled", "false")
            .withEnv("xpack.security.http.ssl.enabled", "false")
            .withStartupTimeout(Duration.of(1, ChronoUnit.MINUTES))
    }
}