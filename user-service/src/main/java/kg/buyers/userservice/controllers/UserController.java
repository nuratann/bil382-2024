package kg.buyers.userservice.controllers;

import kg.buyers.userservice.dto.UserRegistrationDTO;
import kg.buyers.userservice.entities.User;
import kg.buyers.userservice.services.KeycloakUserService;
import kg.buyers.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private  final KeycloakUserService keycloakUserService;

    @Autowired
    public UserController(UserService userService, KeycloakUserService keycloakUserService) {
        this.userService = userService;
        this.keycloakUserService = keycloakUserService;
    }

    @CrossOrigin("*")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> optionalUser = userService.findById(id);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        var KCUser = keycloakUserService.createUser(userRegistrationDTO.toKeycloakUserDTO());
        System.out.println(KCUser.getId());
        User savedUser = userService.save(userRegistrationDTO,KCUser.getId());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/mass")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<UserRegistrationDTO> userRegistrationDTOs) {
        List<User> saved = new ArrayList<>();
        for(UserRegistrationDTO userRegistrationDTO : userRegistrationDTOs) {
            var KCUser = keycloakUserService.createUser(userRegistrationDTO.toKeycloakUserDTO());
            System.out.println(KCUser.getId());
            User savedUser = userService.save(userRegistrationDTO, KCUser.getId());
            saved.add(savedUser);
        }
        return new ResponseEntity<List<User>>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId){
        userService.delete(userId);
        keycloakUserService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //TODO:updateUser, change password, avatarImg
//    @PutMapping
//    public ResponseEntity<User> updateUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
//
//    }
}
