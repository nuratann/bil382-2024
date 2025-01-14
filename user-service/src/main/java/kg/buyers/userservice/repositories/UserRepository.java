package kg.buyers.userservice.repositories;

import kg.buyers.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
