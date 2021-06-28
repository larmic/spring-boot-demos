package de.larmic.properties.service;

import de.larmic.properties.PropertiesApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private final Logger logger = LoggerFactory.getLogger(PropertiesApplication.class);

    private final HelloProperties helloProperties;

    public HelloService(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    public void logHelloProperties() {
        logger.info("Properties (hello.value): " + helloProperties.getValue());
    }
}
