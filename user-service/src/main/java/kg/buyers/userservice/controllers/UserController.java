package kg.buyers.userservice.controllers;

import kg.buyers.userservice.dto.UserRegistrationDTO;
import kg.buyers.userservice.entities.Product;
import kg.buyers.userservice.entities.User;
import kg.buyers.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    @CrossOrigin("*")
    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User savedUser = userService.save(userRegistrationDTO);
        if(savedUser==null) return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @CrossOrigin("*")
    @PutMapping("/{userId}")
    public  ResponseEntity<User> updateUser(@RequestBody UserRegistrationDTO userRegistrationDTO, @PathVariable String userId){
        return new ResponseEntity<>(userService.save(userRegistrationDTO), HttpStatus.OK);
    }

    @CrossOrigin("*")
    @PostMapping("/mass")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<UserRegistrationDTO> userRegistrationDTOs) {
        List<User> saved = new ArrayList<>();
        for(UserRegistrationDTO userRegistrationDTO : userRegistrationDTOs) {
            User savedUser = userService.save(userRegistrationDTO);
            saved.add(savedUser);
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @CrossOrigin("*")
    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId){
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //TODO:updateUser, change password, avatarImg
//    @PutMapping
//    public ResponseEntity<User> updateUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
//
//    }

    @CrossOrigin("*")
    @PostMapping("/{userId}/cart")
    public ResponseEntity<Product> addToCart(@PathVariable String userId, @RequestBody Product product){
        var user = userService.findById(userId).orElse(null);
        if(user==null)return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        user.getCart().add(product);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }
    @CrossOrigin("*")
    @GetMapping("/{userId}/cart")
    public ResponseEntity<Set<Product>> getCart(@PathVariable String  userId){
        var user = userService.findById(userId).orElse(null);
        if(user==null)return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user.getCart(),HttpStatus.OK);
    }

    @CrossOrigin("*")
    @DeleteMapping("/{userId}/cart")
    public ResponseEntity<Product> deleteCartItem(@PathVariable String  userId, @RequestBody Product product){
        var user = userService.findById(userId).orElse(null);
        if(user==null)return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        user.getCart().remove(product);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
    @CrossOrigin("*")
    @PostMapping("/{userId}/favorites")
    public ResponseEntity<Product> addToFavorites(@PathVariable String userId, @RequestBody Product product){
        var user = userService.findById(userId).orElse(null);
        if(user==null)return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        user.getFavorites().add(product);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }
    @CrossOrigin("*")
    @GetMapping("/{userId}/favorites")
    public ResponseEntity<Set<Product>> getFavorites(@PathVariable String  userId){
        var user = userService.findById(userId).orElse(null);
        if(user==null)return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user.getFavorites(),HttpStatus.OK);
    }

    @CrossOrigin("*")
    @DeleteMapping("/{userId}/favorites")
    public ResponseEntity<Product> deleteFavoritesItem(@PathVariable String  userId, @RequestBody Product product){
        var user = userService.findById(userId).orElse(null);
        if(user==null)return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        user.getFavorites().remove(product);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
}
