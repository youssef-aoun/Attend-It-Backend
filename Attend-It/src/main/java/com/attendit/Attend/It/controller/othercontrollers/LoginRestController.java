package com.attendit.Attend.It.controller.othercontrollers;

import com.attendit.Attend.It.dto.LoginRequest;
import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class LoginRestController {
    private final UserService userService;

    @Autowired
    public LoginRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest userLoginRequest) {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();

        boolean isAuthenticated = customAuthenticationLogic(username, password);

        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        // If authentication is successful, you can proceed with setting up authentication context
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("User logged in successfully");
    }

    private boolean customAuthenticationLogic(String username, String password) {
        // Implement your custom authentication logic here
        // For example, you can check if the provided username and password match a user record in your database
        User user = userService.findUserByUsername(username);
        return user != null && (new BCryptPasswordEncoder().matches(password, user.getPassword()));
    }
}
