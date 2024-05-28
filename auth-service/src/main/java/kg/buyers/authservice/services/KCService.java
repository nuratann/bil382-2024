package kg.buyers.authservice.services;

import kg.buyers.authservice.dto.KeycloakUserDTO;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.Map;

@Service
public class KCService {
    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.credentials.username}")
    private String adminUsername;
    @Value("${keycloak.credentials.password}")
    private String adminPassword;
    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    private final RestTemplate restTemplate = new RestTemplate();

    public String getAdminAccessToken() {
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", keycloakServerUrl, "master");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "username=" + adminUsername + "&password=" + adminPassword + "&grant_type=password&client_id=admin-cli";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);
            return (String) response.getBody().get("access_token");
        } catch (HttpClientErrorException e) {
            // Логирование ошибки для лучшего понимания причины
            System.err.println("Error while getting admin access token: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw e;  // Перебросить исключение после логирования
        }
//        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);
//        return (String) response.getBody().get("access_token");
    }

    public Map<String, String> getUserTokens(String username, String password) {
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", keycloakServerUrl, realm);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "username=" + username + "&password=" + password + "&grant_type=password&client_id=" + clientId+ "&client_secret=" + clientSecret;
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);
        Map<String, String> tokens = Map.of(
                "access_token", (String) response.getBody().get("access_token"),
                "refresh_token", (String) response.getBody().get("refresh_token")
        );
        return tokens;
    }

    public Map<String,String> createUser(KeycloakUserDTO userDTO) {
        String accessToken = getAdminAccessToken();
        String url = String.format("%s/admin/realms/%s/users", keycloakServerUrl, realm);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> user = Map.of(
                "username", userDTO.getUsername(),
                "email", userDTO.getEmail(),
                "firstName", userDTO.getFirstName(),
                "lastName", userDTO.getLastName(),
                "enabled", true,
                "emailVerified", true,
                "credentials", Collections.singletonList(Map.of(
                        "type", "password",
                        "value", userDTO.getPassword(),
                        "temporary", false
                ))
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(user, headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        return getUserTokens(userDTO.getUsername(), userDTO.getPassword());
    }

    public Map<String,String> updateUser(String userId, KeycloakUserDTO userDTO) {
        String accessToken = getAdminAccessToken();
        String url = String.format("%s/admin/realms/%s/users/%s", keycloakServerUrl, realm, userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> user = Map.of(
                "username", userDTO.getUsername(),
                "email", userDTO.getEmail(),
                "firstName", userDTO.getFirstName(),
                "lastName", userDTO.getLastName(),
                "enabled", true,
                "emailVerified", true
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(user, headers);
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        return getUserTokens(userDTO.getUsername(), userDTO.getPassword());
    }


    public Map<String, String> refreshTokens(String refreshToken) {
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", keycloakServerUrl, realm);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "grant_type=refresh_token&refresh_token=" + refreshToken + "&client_id=" + clientId;
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);
        Map<String, String> tokens = Map.of(
                "access_token", (String) response.getBody().get("access_token"),
                "refresh_token", (String) response.getBody().get("refresh_token")
        );
        return tokens;
    }
}
