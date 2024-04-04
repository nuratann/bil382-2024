package kg.buyers.userservice.services;

import kg.buyers.userservice.dto.KeycloakUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class KeycloakUserService {

    @Value("${keycloak.realm}")
    private String realmName;
    private final Keycloak keycloak;

    public KeycloakUserService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public UserRepresentation createUser(KeycloakUserDTO userDto){
        var userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userDto.getUsername());
        userRepresentation.setEmail(userDto.getEmail());
        userRepresentation.setLastName(userDto.getLastName());
        userRepresentation.setFirstName(userDto.getFirstName());
        userRepresentation.setEmailVerified(true);

        var credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userDto.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        var credentials = new ArrayList<CredentialRepresentation>();
        credentials.add(credentialRepresentation);

        userRepresentation.setCredentials(credentials);

        var usersResource = keycloak.realm("buyers_realm").users();
        var response = usersResource.create(userRepresentation);
        return usersResource.search(userRepresentation.getUsername()).getFirst();
    }

    public UserRepresentation getUserById(String userId){
        return keycloak.realm(realmName).users().get(userId).toRepresentation();
    }

    public UserRepresentation deleteUserById(String userId){
        var deletedUser =  keycloak.realm(realmName).users().get(userId).toRepresentation();
        keycloak.realm(realmName).users().delete(userId);
        return deletedUser;
    }
}
