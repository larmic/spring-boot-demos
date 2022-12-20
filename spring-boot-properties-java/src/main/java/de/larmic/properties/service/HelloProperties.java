package de.larmic.properties.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hello")
public record HelloProperties(String value) {

}
