package de.larmic.keycloak.service1.security;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
class KeycloakSecurityConfig {

    private static final String DECODING_ERROR_MESSAGE_TEMPLATE = "An error occurred while attempting to decode the Jwt: %s";

    private final KeycloakLogoutHandler keycloakLogoutHandler;

    KeycloakSecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(it -> it
            .requestMatchers("/unsecure/*").permitAll()
            .requestMatchers("/secure/*").hasRole("custom_realm_role"))
            .csrf().disable();

        http.oauth2Login()
            .and()
            .logout()
            .addLogoutHandler(keycloakLogoutHandler)
            .logoutSuccessUrl("/");
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        final var defaultAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        defaultAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        defaultAuthoritiesConverter.setAuthoritiesClaimName("realm_access");

        final var keycloakAuthoritiesConverter = new KeycloakGrantedAuthoritiesConverter();

        final var authoritiesConverter = new DelegatingJwtGrantedAuthoritiesConverter(defaultAuthoritiesConverter, keycloakAuthoritiesConverter);

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return jwtAuthenticationConverter;
    }

    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    @Bean(name = "oidcUserService")
    OAuth2UserService<OidcUserRequest, OidcUser> getOidcUserService() {
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
                final var oidcUser = (DefaultOidcUser) super.loadUser(userRequest);
                final var jwtAsString = userRequest.getAccessToken().getTokenValue();
                final var jwt = createJwt(jwtAsString);

                final var authorities = new KeycloakGrantedAuthoritiesConverter().convert(jwt);
                authorities.addAll(oidcUser.getAuthorities());

                final var defaultOidcUser = new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

                return defaultOidcUser;
            }

            private Jwt createJwt(String jwtAsString) {
                try {
                    final var test = JWTParser.parse(jwtAsString);
                    return createJwt(jwtAsString, test);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            private Jwt createJwt(String token, JWT parsedJwt) {
                try {
                    Map<String, Object> headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
                    Map<String, Object> claims = parsedJwt.getJWTClaimsSet().getClaims();

                    var t = claims
                        .entrySet()
                        .stream()
                        .map(entry -> {
                            if (entry.getKey().equals("iat")){
                                return new AbstractMap.SimpleEntry<>("iat", ((Date) entry.getValue()).toInstant());
                            }
                            if (entry.getKey().equals("exp")){
                                return new AbstractMap.SimpleEntry<>("exp", ((Date) entry.getValue()).toInstant());
                            }
                            return entry;
                        })
                        .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

                    return Jwt.withTokenValue(token)
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

    //@Bean
    @SuppressWarnings("unchecked")
    public GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak() {
        return authorities -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            var authority = authorities.iterator().next();
            boolean isOidc = authority instanceof OidcUserAuthority;

            if (isOidc) {
                var oidcUserAuthority = (OidcUserAuthority) authority;
                var userInfo = oidcUserAuthority.getUserInfo();

                if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
                    var realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM);
                    var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
                }
            } else {
                var oauth2UserAuthority = (OAuth2UserAuthority) authority;
                Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                if (userAttributes.containsKey(REALM_ACCESS_CLAIM)) {
                    var realmAccess = (Map<String, Object>) userAttributes.get(REALM_ACCESS_CLAIM);
                    var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
                }
            }
            return mappedAuthorities;
        };
    }

    Collection<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
    }
}