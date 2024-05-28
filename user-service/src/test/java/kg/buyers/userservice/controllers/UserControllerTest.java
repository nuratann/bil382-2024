package kg.buyers.userservice.controllers;


import kg.buyers.userservice.dto.UserRegistrationDTO;
import kg.buyers.userservice.entities.User;
import kg.buyers.userservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGetUserById_Success() {
        User user = new User();
        user.setUsername("testUser");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        when(userService.findById("1")).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userService.findById("1")).thenReturn(null);

        ResponseEntity<User> response = userController.getUserById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUserById_Unauthorized() {
        User user = new User();
        user.setUsername("testUser");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("otherUser");
        when(userService.findById("1")).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById("1");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testCreateUser_Success() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        User user = new User();

        when(userService.save(dto)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testCreateUser_Conflict() {
        UserRegistrationDTO dto = new UserRegistrationDTO();

        when(userService.save(dto)).thenReturn(null);

        ResponseEntity<User> response = userController.createUser(dto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Collections.singletonList(new User());
        when(userService.findAll()).thenReturn(users);

        List<User> result = userController.getAllUsers();

        assertEquals(users, result);
    }

    // Add more tests for other methods in UserController

}
