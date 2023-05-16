package de.larmic.keycloak.service1.security;

import com.nimbusds.jwt.JWTParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.text.ParseException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
class KeycloakSecurityConfig {

    private static final String DECODING_ERROR_MESSAGE_TEMPLATE = "An error occurred while attempting to decode the Jwt: %s";

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(it -> it
                .requestMatchers("/unsecure/*").permitAll()
                .requestMatchers("/secure/*").hasAnyRole("custom_client1_role", "custom_realm_role"))
            .csrf().disable();

        http.oauth2Login()
            .and()
            .logout()
            .logoutSuccessUrl("/");
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }

    // enable custom role mapper. spring security does not support keycloak mapping by default.
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(KeycloakGrantedAuthoritiesConverter keycloakGrantedAuthoritiesConverter) {
        final var defaultAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        defaultAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        defaultAuthoritiesConverter.setAuthoritiesClaimName("realm_access");

        final var authoritiesConverter = new DelegatingJwtGrantedAuthoritiesConverter(defaultAuthoritiesConverter, keycloakGrantedAuthoritiesConverter);

        final var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return jwtAuthenticationConverter;
    }

    // required for getting browser redirect to keycloak login
    // if no browser redirect is required this bean could be removed
    @Bean(name = "oidcUserService")
    OAuth2UserService<OidcUserRequest, OidcUser> getOidcUserService(KeycloakGrantedAuthoritiesConverter keycloakGrantedAuthoritiesConverter) {
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
                final var oidcUser = (DefaultOidcUser) super.loadUser(userRequest);
                final var jwtAsString = userRequest.getAccessToken().getTokenValue();
                final var jwt = createJwt(jwtAsString);

                final var authorities = keycloakGrantedAuthoritiesConverter.convert(jwt);
                authorities.addAll(oidcUser.getAuthorities());

                return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            }

            private Jwt createJwt(String jwtAsString) {
                try {
                    final var parsedJwt = JWTParser.parse(jwtAsString);
                    final var headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
                    final var claims = parsedJwt.getJWTClaimsSet().getClaims();

                    var t = claims
                        .entrySet()
                        .stream()
                        .map(entry -> {
                            if (entry.getKey().equals("iat")) {
                                return new AbstractMap.SimpleEntry<>("iat", ((Date) entry.getValue()).toInstant());
                            }
                            if (entry.getKey().equals("exp")) {
                                return new AbstractMap.SimpleEntry<>("exp", ((Date) entry.getValue()).toInstant());
                            }
                            return entry;
                        })
                        .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

                    return Jwt.withTokenValue(jwtAsString)
                        .headers(h -> h.putAll(headers))
                        .claims(c -> c.putAll(t))
                        .build();
                } catch (Exception ex) {
                    if (ex.getCause() instanceof ParseException) {
                        throw new JwtException(String.format(DECODING_ERROR_MESSAGE_TEMPLATE, "Malformed payload"));
                    } else {
                        throw new JwtException(String.format(DECODING_ERROR_MESSAGE_TEMPLATE, ex.getMessage()), ex);
                    }
                }
            }
        };
    }
}