package com.attendit.Attend.It.controller.othercontrollers;

import com.attendit.Attend.It.dto.LoginRequest;
import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.responses.errors.LoginErrorResponse;
import com.attendit.Attend.It.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class LoginRestController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    //private final JWTService jwtService;
    /*@Autowired
    public LoginRestController(UserService userService,
                               BCryptPasswordEncoder passwordEncoder,
                               UserDetailsService userDetailsService,
                               JWTService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }*/

    public LoginRestController(UserService userService, BCryptPasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Autowired


    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest userLoginRequest) {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();

        // Check if the provided username and password are valid
        if (isValidCredentials(username, password)) {
            // Generate JWT token or retrieve user details
            String jwtToken = "Logged in successfully";//generateJwtToken(username);
            // Return the JWT token or user details in the response
            return ResponseEntity.ok().body(jwtToken);
        } else {
            // Return an unauthorized response for invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginErrorResponse("Invalid username or password"));
        }
    }

    private boolean isValidCredentials(String username, String password) {
        // Retrieve user by username
        User user = userService.findUserByUsername(username);
        // Check if the user exists and the password matches
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    /*private String generateJwtToken(String username) {
        // Retrieve user details from UserDetailsService or database
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // Generate JWT token using JWTService
        return jwtService.generateToken(userDetails);
    }*/

}