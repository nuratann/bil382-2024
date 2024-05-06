package kg.buyers.userservice.services;

import kg.buyers.userservice.dto.UserRegistrationDTO;
import kg.buyers.userservice.entities.User;
import kg.buyers.userservice.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User save(UserRegistrationDTO userRegistrationDTO) {
        if(userRepository.existsByEmail(userRegistrationDTO.getEmail()) || userRepository.existsByUsername(userRegistrationDTO.getUsername())){
            return null;
        }

        return userRepository.save(userDtoToUser(userRegistrationDTO));
    }

    private User userDtoToUser(UserRegistrationDTO userRegistrationDTO){
        return User.builder()
                .id(userRegistrationDTO.getId())
                .username(userRegistrationDTO.getUsername())
                .firstName(userRegistrationDTO.getFirstName())
                .lastName(userRegistrationDTO.getLastName())
                .birthDate(userRegistrationDTO.getBirthDate())
                .email(userRegistrationDTO.getEmail())
                .phone(userRegistrationDTO.getPhone())
                .gender(userRegistrationDTO.getGender())
                .avatarImg(null)
                .build();
    }

    public void save(User user) {userRepository.save(user);}

    public User update(UserRegistrationDTO userRegistrationDTO){
        return userRepository.save(userDtoToUser(userRegistrationDTO));
    }

    public void delete(String userId){
        userRepository.deleteById(userId);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
}
