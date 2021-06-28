package de.larmic.properties.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloServiceTest {

    @Test
    void testme() {
        HelloService helloService = new HelloService(new HelloProperties("hello"));
        helloService.logHelloProperties();
    }
}