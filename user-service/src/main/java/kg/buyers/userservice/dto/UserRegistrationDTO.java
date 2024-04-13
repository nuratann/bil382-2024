package kg.buyers.userservice.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String phone;
    private String gender;
    private String password;
    private String conflictField;
    //private String avatarImg;

    public KeycloakUserDTO toKeycloakUserDTO(){
        var dto = new KeycloakUserDTO();
        dto.setUsername(this.username);
        dto.setEmail(this.email);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setPassword(this.password);

        return dto;
    }
}
