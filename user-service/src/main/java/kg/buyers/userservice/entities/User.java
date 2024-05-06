package kg.buyers.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String phone;
    private String gender;
    private String avatarImg;
    @ElementCollection
    private Set<String> favorites;
    @ElementCollection
    private Set<String> cart;
}
