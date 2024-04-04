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

    public Optional<User> findById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User save(UserRegistrationDTO userRegistrationDTO, String KCid) {
        User user = User.builder()
                .login(userRegistrationDTO.getLogin())
                .firstName(userRegistrationDTO.getFirstName())
                .lastName(userRegistrationDTO.getLastName())
                .birthDay(userRegistrationDTO.getBirthDay())
                .email(userRegistrationDTO.getEmail())
                .phone(userRegistrationDTO.getPhone())
                .gender(userRegistrationDTO.getGender())
                .avatarImg(null)
                .KCid(KCid)
                .build();

        return userRepository.save(user);
    }

    public String delete(Long userId){
        String KCid = userRepository.findById(userId).get().getKCid();
        userRepository.deleteById(userId);
        return KCid;
    }
}
