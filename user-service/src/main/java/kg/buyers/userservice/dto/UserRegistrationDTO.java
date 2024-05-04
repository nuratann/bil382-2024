package kg.buyers.userservice.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String id;
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

}
