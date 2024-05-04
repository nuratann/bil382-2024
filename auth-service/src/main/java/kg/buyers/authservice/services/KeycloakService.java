package kg.buyers.authservice.services;

import kg.buyers.authservice.dto.KeycloakUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@Slf4j
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final Keycloak keycloak;

    @Autowired
    public KeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public UserRepresentation createUser(KeycloakUserDTO userDto){
        var userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userDto.getUsername());
        userRepresentation.setEmail(userDto.getEmail());
        userRepresentation.setLastName(userDto.getLastName());
        userRepresentation.setFirstName(userDto.getFirstName());
        userRepresentation.setEmailVerified(false);

        var credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userDto.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        var credentials = new ArrayList<CredentialRepresentation>();
        credentials.add(credentialRepresentation);

        userRepresentation.setCredentials(credentials);

        keycloak.realm(realm).users().create(userRepresentation);
        return keycloak.realm(realm).users().search(userRepresentation.getUsername()).getFirst();
    }

    public UserRepresentation getUserById(String userId){
        return keycloak.realm(realm).users().get(userId).toRepresentation();
    }

    public UserRepresentation deleteUserById(String userId){
        var deletedUser =  keycloak.realm(realm).users().get(userId).toRepresentation();
        keycloak.realm(realm).users().delete(userId);
        return deletedUser;
    }



    public AccessTokenResponse getToken(String username, String passwordOrToken, String type) {
        String tokenUrl = serverUrl+"/realms/"+realm+"/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        if(type.equals("password")) {
            map.add("grant_type", "password");
            map.add("password", passwordOrToken);
            map.add("username", username);
        } else if(type.equals("refresh_token")){
            map.add("grant_type", "refresh_token");
            map.add("refresh_token", passwordOrToken);
            map.add("username", username);
        } else {
            map.add("grant_type", "urn:ietf:params:oauth:grant-type:token-exchange");
            map.add("subject_token", passwordOrToken);
            map.add("subject_token_type", "urn:ietf:params:oauth:token-type:access_token");
        }
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);



        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessTokenResponse> responseEntity = restTemplate.postForEntity(tokenUrl, request, AccessTokenResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            // Обработка ошибок
            return null;
        }
    }



}