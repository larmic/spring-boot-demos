package de.larmic.keycloak.service2.rest;

import de.larmic.keycloak.service2.client.SpringBootService1Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {

    private final SpringBootService1Client client;

    public SecureController(SpringBootService1Client client) {
        this.client = client;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok(client.getSecureHello());
    }
}
