package kg.buyers.authservice.dto;

import lombok.Data;

@Data
public class KeycloakUserDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
