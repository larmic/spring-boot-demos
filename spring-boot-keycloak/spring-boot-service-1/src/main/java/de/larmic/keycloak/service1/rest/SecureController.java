package de.larmic.keycloak.service1.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello secure");
    }

}
