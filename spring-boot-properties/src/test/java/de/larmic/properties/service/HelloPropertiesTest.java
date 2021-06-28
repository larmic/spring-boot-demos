package de.larmic.properties.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HelloPropertiesTest {

    @Autowired
    private HelloProperties properties;

    @Test
    void assertProperties() {
        assertThat(properties.getValue()).isEqualTo("test properties");
    }
}