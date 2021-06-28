package de.larmic.keycloak.service2.client;

import de.larmic.keycloak.service2.security.AccessTokenFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpringBootService1Client {

    private final RestTemplate restTemplate;
    private final AccessTokenFactory accessTokenFactory;

    public SpringBootService1Client(final RestTemplate restTemplate, AccessTokenFactory accessTokenFactory) {
        this.restTemplate = restTemplate;
        this.accessTokenFactory = accessTokenFactory;
    }

    public String getSecureHello() {
        final var accessToken = accessTokenFactory.getAccessToken().get();
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        final var entity = new HttpEntity<>(headers);

        final var response = restTemplate.exchange("http://localhost:8081/secure/hello", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getUnsecureHello() {
        final var response = restTemplate.getForEntity("http://localhost:8081/unsecure/hello", String.class);
        return response.getBody();
    }

}
