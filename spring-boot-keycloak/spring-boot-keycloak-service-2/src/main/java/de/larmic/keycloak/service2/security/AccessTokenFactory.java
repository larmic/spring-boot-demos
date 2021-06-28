package de.larmic.keycloak.service2.security;

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessTokenFactory {

    public Optional<String> getAccessToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return Optional.of(((SimpleKeycloakAccount) authentication.getDetails()).getKeycloakSecurityContext().getTokenString());
        }

        return Optional.empty();
    }

}
