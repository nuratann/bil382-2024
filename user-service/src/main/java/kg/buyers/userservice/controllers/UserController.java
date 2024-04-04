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

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
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

    @DeleteMapping
    public ResponseEntity<User> deleteUser(@PathVariable Long userId){
        String keycloakId = userService.delete(userId);
        keycloakUserService.deleteUserById(keycloakId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //TODO:updateUser, change password, avatarImg
//    @PutMapping
//    public ResponseEntity<User> updateUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
//
//    }
}
