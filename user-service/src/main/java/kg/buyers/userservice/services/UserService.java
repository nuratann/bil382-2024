package kg.buyers.userservice.services;

import kg.buyers.userservice.dto.UserRegistrationDTO;
import kg.buyers.userservice.entities.User;
import kg.buyers.userservice.repositories.UserRepository;
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

    public Optional<User> findById(@PathVariable String id) {
        return userRepository.findById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User save(UserRegistrationDTO userRegistrationDTO, String KCid) {
        User user = User.builder()
                .id(KCid)
                .username(userRegistrationDTO.getUsername())
                .firstName(userRegistrationDTO.getFirstName())
                .lastName(userRegistrationDTO.getLastName())
                .birthDay(userRegistrationDTO.getBirthDay())
                .email(userRegistrationDTO.getEmail())
                .phone(userRegistrationDTO.getPhone())
                .gender(userRegistrationDTO.getGender())
                .avatarImg(null)
                .build();

        return userRepository.save(user);
    }

    public void delete(String userId){
        userRepository.deleteById(userId);
    }
}
