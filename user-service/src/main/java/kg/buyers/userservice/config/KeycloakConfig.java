package kg.buyers.userservice.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Bean
    public Keycloak keycloak() {
        //return Keycloak.getInstance("http://localhost:5052","buyers_realm","admin","admin","admin-cli");
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:5052")
                .realm("buyers_realm")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
//                .username("admin")
//                .password("admin")
                .clientId("admin-cli")
                .clientSecret("bA27Tt7jfWoeS7PUJVQzSrxwAGe4vSI7")
                .build();
    }
}
