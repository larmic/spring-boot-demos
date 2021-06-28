package de.larmic.keycloak.service1.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unsecure")
public class UnsecureController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello unsecure");
    }
}
