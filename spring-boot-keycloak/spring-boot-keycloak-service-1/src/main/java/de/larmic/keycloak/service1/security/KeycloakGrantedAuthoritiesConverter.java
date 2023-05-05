package de.larmic.keycloak.service1.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.log.LogMessage;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final Log logger = LogFactory.getLog(getClass());

    private static final String REALM_AUTHORITIES_CLAIM_NAME = "realm_access";
    private static final String RESOURCE_AUTHORITIES_CLAIM_NAME = "resource_access";
    private static final String AUTHORITIES_MAP_NAME = "roles";

    private final String keyCloakResourceClientId;

    public KeycloakGrantedAuthoritiesConverter(@Value("SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_KEYCLOAK_CLIENT-ID") String keyCloakResourceClientId) {
        this.keyCloakResourceClientId = keyCloakResourceClientId;
    }

    @Override
    @NonNull
    public Collection<GrantedAuthority> convert(@NonNull  Jwt jwt) {
        return getAuthorities(jwt)
            .stream().map(it -> new SimpleGrantedAuthority("ROLE_" + it))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private Collection<String> getAuthorities(Jwt jwt) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(LogMessage.format("Looking for scopes in claim %s", REALM_AUTHORITIES_CLAIM_NAME));
        }
        final var realmAuthorities = jwt.getClaim(REALM_AUTHORITIES_CLAIM_NAME);
        if (realmAuthorities instanceof Map<?, ?> && ((Map<?, ?>) realmAuthorities).containsKey(AUTHORITIES_MAP_NAME)) {
            return castAuthoritiesToCollection(((Map<?, ?>) realmAuthorities).get(AUTHORITIES_MAP_NAME));
        }

        final var resourceAuthorities = jwt.getClaim(RESOURCE_AUTHORITIES_CLAIM_NAME);

        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private Collection<String> castAuthoritiesToCollection(Object authorities) {
        return (Collection<String>) authorities;
    }
}
