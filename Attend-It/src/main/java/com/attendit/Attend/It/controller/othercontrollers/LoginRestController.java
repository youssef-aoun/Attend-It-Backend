package com.attendit.Attend.It.controller.othercontrollers;

import com.attendit.Attend.It.dto.LoginRequest;
import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.errorresponses.LoginErrorResponse;
import com.attendit.Attend.It.service.authentication.AuthenticationService;
import com.attendit.Attend.It.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final AuthenticationService authenticationService;
    @Autowired
    public LoginRestController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest userLoginRequest) {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();

        boolean isAuthenticated = customAuthenticationLogic(username, password);

        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginErrorResponse("Invalid username or password"));
        }


        authenticationService.authenticateUser(username, password);
        
        return ResponseEntity.ok("User logged in successfully");
    }

    private boolean customAuthenticationLogic(String username, String password) {
        // Implement your custom authentication logic here
        // For example, you can check if the provided username and password match a user record in your database
        User user = userService.findUserByUsername(username);
        return user != null && (new BCryptPasswordEncoder().matches(password, user.getPassword()));
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    // Redirect the user to the homepage if authenticated
    public String redirectToHomepage() {
        if (isAuthenticated()) {
            return "redirect:/homepage"; // Replace "/homepage" with the URL of your homepage
        } else {
            return "redirect:/login"; // Redirect to the login page if not authenticated
        }
    }
}
