package de.larmic.keycloak.service1.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.log.LogMessage;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final Log logger = LogFactory.getLog(getClass());

    private static final String AUTHORITIES_CLAIM_NAME = "realm_access";
    private static final String AUTHORITIES_MAP_NAME = "roles";

    @Override
    @NonNull
    public Collection<GrantedAuthority> convert(@NonNull  Jwt jwt) {
        return getAuthorities(jwt)
            .stream().map(it -> new SimpleGrantedAuthority("ROLE_" + it))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private Collection<String> getAuthorities(Jwt jwt) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(LogMessage.format("Looking for scopes in claim %s", AUTHORITIES_CLAIM_NAME));
        }
        final var authorities = jwt.getClaim(AUTHORITIES_CLAIM_NAME);
        if (authorities instanceof Map<?, ?> && ((Map<?, ?>) authorities).containsKey(AUTHORITIES_MAP_NAME)) {
            return castAuthoritiesToCollection(((Map<?, ?>) authorities).get(AUTHORITIES_MAP_NAME));
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private Collection<String> castAuthoritiesToCollection(Object authorities) {
        return (Collection<String>) authorities;
    }
}
