package com.attendit.Attend.It.controller.othercontrollers;

import com.attendit.Attend.It.dto.LoginRequest;
import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.responses.errors.LoginErrorResponse;
import com.attendit.Attend.It.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class LoginRestController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public LoginRestController(UserService userService,
                               BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
        return ResponseEntity.ok("User logged in successfully.");
    }

    private boolean isValidCredentials(String username, String password) {
        // Retrieve user by username
        User user = userService.findUserByUsername(username);
        // Check if the user exists and the password matches
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

}