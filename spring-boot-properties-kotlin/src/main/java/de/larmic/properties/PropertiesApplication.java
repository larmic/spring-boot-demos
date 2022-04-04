package de.larmic.properties;

import de.larmic.properties.service.HelloProperties;
import de.larmic.properties.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = HelloProperties.class)
public class PropertiesApplication {

    public static void main(String[] args) {
        final var app = SpringApplication.run(PropertiesApplication.class, args);
        final var helloService = app.getBean(HelloService.class);
        helloService.logHelloProperties();
    }

}