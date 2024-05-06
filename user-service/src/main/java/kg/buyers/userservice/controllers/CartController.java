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
@RequestMapping("api/v1/carts")
public class CartController {
    UserService userService;

    public CartController(UserService userService) {
        this.userService = userService;
    }

    private boolean userCheck(@NonNull User user){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name.equals(user.getUsername());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Set<String>> getCart(@PathVariable String userId){
        User user = userService.findById(userId);
        if(userCheck(user))
            return new ResponseEntity<>(user.getCart(), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Set<String>> updateCart(@PathVariable String userId, @RequestBody Set<String> cart){
        User user = userService.findById(userId);
        if(userCheck(user)) {
            user.setCart(cart);
            userService.save(user);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addToCart(@PathVariable String userId, @RequestBody String productId){
        User user = userService.findById(userId);
        if(userCheck(user)) {
            user.getCart().add(productId);
            userService.save(user);
            return new ResponseEntity<>(productId,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteFromCart(@PathVariable String userId, @RequestBody String productId){
        User user = userService.findById(userId);
        if(userCheck(user)) {
            user.getCart().remove(productId);
            userService.save(user);
            return new ResponseEntity<>(productId,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }
}
