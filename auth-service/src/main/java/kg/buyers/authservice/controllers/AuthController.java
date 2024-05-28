package kg.buyers.authservice.controllers;


import kg.buyers.authservice.dto.KeycloakUserDTO;
import kg.buyers.authservice.dto.SignInDTO;
import kg.buyers.authservice.services.KCService;
import kg.buyers.authservice.services.KeycloakService;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin("*")
public class AuthController {
//    private final KeycloakService keycloakService;
    private final KCService kcService;


    @Autowired
    public AuthController( KCService kcService) {
//        this.keycloakService = keycloakService;
        this.kcService = kcService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Map<String,String>> signUp(@RequestBody KeycloakUserDTO account){
//        keycloakService.createUser(account);
//        var token = keycloakService.getToken(account.getUsername(), account.getPassword(),"password");
//        return new ResponseEntity<>(token, HttpStatus.OK);
        return new ResponseEntity<>(kcService.createUser(account), HttpStatus.CREATED);
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<Map<String,String>> update(@RequestBody KeycloakUserDTO account, @PathVariable String userId){
//        keycloakService.createUser(account);
//        var token = keycloakService.getToken(account.getUsername(), account.getPassword(),"password");
//        return new ResponseEntity<>(token, HttpStatus.OK);
        return new ResponseEntity<>(kcService.updateUser(userId,account), HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<Map<String,String>> signIn(@RequestBody SignInDTO sign){
//        return new ResponseEntity<>(keycloakService.getToken(sign.getUsername(),sign.getPassword(), "password"), HttpStatus.OK);
        return new ResponseEntity<>(kcService.getUserTokens(sign.getUsername(), sign.getPassword()), HttpStatus.OK);
    }

//    @PostMapping("/signInWithSocial")
//    public ResponseEntity<AccessTokenResponse> signInSocial(@RequestBody String token){
//        return new ResponseEntity<>(keycloakService.getToken("", token, "social"), HttpStatus.OK);
//    }

    @GetMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody String refreshToken){
//        return new ResponseEntity<>(keycloakService.getToken(sign.getUsername(),sign.getPassword(), "refresh_token"), HttpStatus.OK);
        return new ResponseEntity<>(kcService.refreshTokens(refreshToken), HttpStatus.OK);
    }
}
