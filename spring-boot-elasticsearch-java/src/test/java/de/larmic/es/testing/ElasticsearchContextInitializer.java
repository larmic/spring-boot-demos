package de.larmic.es.testing;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

public class ElasticsearchContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final ElasticsearchContainer elasticsearch = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.14.0");

    @Override
    public void initialize(final ConfigurableApplicationContext context) {
        elasticsearch.start();
        TestPropertyValues.of(
            "spring.elasticsearch.rest.uris=" + elasticsearch.getHttpHostAddress()
        ).applyTo(context.getEnvironment());
    }
}
