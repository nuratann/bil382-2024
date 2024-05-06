package kg.buyers.userservice.controllers;
import kg.buyers.userservice.entities.User;
import kg.buyers.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/favorites")
public class FavoritesController {
    UserService userService;

    public FavoritesController(UserService userService) {
        this.userService = userService;
    }

    private boolean userCheck(@NonNull User user){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name.equals(user.getUsername());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Set<String>> getFavorites(@PathVariable String userId){
        User user = userService.findById(userId);
        if(userCheck(user))
            return new ResponseEntity<>(user.getFavorites(), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Set<String>> updateFavorites(@PathVariable String userId, @RequestBody Set<String> favorites){
        User user = userService.findById(userId);
        if(userCheck(user)) {
            user.setCart(favorites);
            userService.save(user);
            return new ResponseEntity<>(favorites, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addToFavorites(@PathVariable String userId, @RequestBody String favoriteId){
        User user = userService.findById(userId);
        if(userCheck(user)) {
            user.getCart().add(favoriteId);
            userService.save(user);
            return new ResponseEntity<>(favoriteId,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteFromCart(@PathVariable String userId, @RequestBody String favoriteId){
        User user = userService.findById(userId);
        if(userCheck(user)) {
            user.getFavorites().remove(favoriteId);
            userService.save(user);
            return new ResponseEntity<>(favoriteId,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }
}