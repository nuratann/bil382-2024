package kg.buyers.userservice.entities;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String KCid;
    private String login;
    private String firstName;
    private String lastName;
    private String birthDay;
    private String email;
    private String phone;
    private String gender;
    private String avatarImg;
}
