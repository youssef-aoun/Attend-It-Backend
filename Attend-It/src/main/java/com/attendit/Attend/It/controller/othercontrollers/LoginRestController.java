package com.attendit.Attend.It.controller.othercontrollers;

import com.attendit.Attend.It.dto.LoginRequest;
import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.responses.errors.LoginErrorResponse;
import com.attendit.Attend.It.security.authentication.AuthenticationService;
import com.attendit.Attend.It.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class LoginRestController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationService authenticationService;

    @Autowired
    public LoginRestController(UserService userService,
                               BCryptPasswordEncoder passwordEncoder,
                               AuthenticationService authenticationService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest userLoginRequest, HttpServletRequest request) {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();

        if(isValidCredentials(username, password)) {
            password = userService.findUserByUsername(username).getPassword();
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginErrorResponse("Invalid username or password"));
        }
        try {
            UserDetails userDetails = authenticationService.authenticate(username, password);
            // Set authentication object in SecurityContext
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()));
            // Create session
            request.getSession(true);
            // Return success response
            return ResponseEntity.ok().body("Logged in successfully");
        } catch (AuthenticationException e) {
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

    @GetMapping("/home")
    public String homePage(){
        System.out.println("Welcome to our application!");
        return "Welcome to our application!";
    }

}