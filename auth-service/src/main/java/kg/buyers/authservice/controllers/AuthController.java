package kg.buyers.authservice.controllers;


import kg.buyers.authservice.dto.KeycloakUserDTO;
import kg.buyers.authservice.dto.SignInDTO;
import kg.buyers.authservice.services.KeycloakService;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final KeycloakService keycloakService;

    @Autowired
    public AuthController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<AccessTokenResponse> signUp(@RequestBody KeycloakUserDTO account){
        keycloakService.createUser(account);
        var token = keycloakService.getToken(account.getUsername(), account.getPassword(),"password");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AccessTokenResponse> signIn(@RequestBody SignInDTO sign){
        return new ResponseEntity<>(keycloakService.getToken(sign.getUsername(),sign.getPassword(), "password"), HttpStatus.OK);
    }

    @PostMapping("/signInWithSocial")
    public ResponseEntity<AccessTokenResponse> signInSocial(@RequestBody String token){
        return new ResponseEntity<>(keycloakService.getToken("", token, "social"), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refreshToken(@RequestBody SignInDTO sign){
        return new ResponseEntity<>(keycloakService.getToken(sign.getUsername(),sign.getPassword(), "refresh_token"), HttpStatus.OK);
    }
}
