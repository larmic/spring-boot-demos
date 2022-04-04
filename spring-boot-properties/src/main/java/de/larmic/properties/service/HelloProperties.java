package de.larmic.properties.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "hello")
@ConstructorBinding
public record HelloProperties(String value) {

}
