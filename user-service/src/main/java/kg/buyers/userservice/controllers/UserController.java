package kg.buyers.userservice.controllers;

import kg.buyers.userservice.dto.UserRegistrationDTO;
import kg.buyers.userservice.entities.User;
import kg.buyers.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private boolean userCheck(@NonNull User user){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name.equals(user.getUsername());
    }

//ushul func testirovanie kildim
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userService.findById(userId);
        if(user==null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else if(userCheck(user)) return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    //ushul func testirovanie kildim
    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User savedUser = userService.save(userRegistrationDTO);
        if(savedUser==null) return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    @PostMapping("/{userId}")
    public  ResponseEntity<User> updateUser(@RequestBody UserRegistrationDTO userRegistrationDTO, @PathVariable String userId){
        return new ResponseEntity<>(userService.update(userRegistrationDTO), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/bulk")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<UserRegistrationDTO> userRegistrationDTOs) {
        List<User> saved = new ArrayList<>();
        userRegistrationDTOs.forEach(dto ->
            {
                User user = userService.save(dto);
                saved.add(user);
            });
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId){
        User user = userService.findById(userId);
        if(userCheck(user)){
            userService.delete(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/{userId}")
    public ResponseEntity<User> deleteUserByAdmin(@PathVariable String userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
