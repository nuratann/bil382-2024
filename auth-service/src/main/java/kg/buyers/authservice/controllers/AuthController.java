package kg.buyers.authservice.controllers;


import kg.buyers.authservice.dto.TokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @PostMapping("/signUp")
    public ResponseEntity<TokenDTO> signUp(){
        return new ResponseEntity<>(new TokenDTO(), HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenDTO> signIn(){
        return new ResponseEntity<>(new TokenDTO(), HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity<TokenDTO> getToken(){
        return new ResponseEntity<>(new TokenDTO(), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenDTO> refreshToken(){
        return new ResponseEntity<>(new TokenDTO(), HttpStatus.OK);
    }
}
