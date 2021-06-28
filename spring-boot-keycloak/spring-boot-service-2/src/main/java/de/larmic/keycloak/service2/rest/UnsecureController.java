package de.larmic.keycloak.service2.rest;

import de.larmic.keycloak.service2.client.SpringBootService1Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unsecure")
public class UnsecureController {

    private final SpringBootService1Client client;

    public UnsecureController(final SpringBootService1Client client) {
        this.client = client;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok(client.getUnsecureHello());
    }
}
