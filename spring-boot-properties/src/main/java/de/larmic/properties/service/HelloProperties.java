package de.larmic.properties.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "hello")
@ConstructorBinding
public class HelloProperties {

    private final String value;

    public HelloProperties(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
