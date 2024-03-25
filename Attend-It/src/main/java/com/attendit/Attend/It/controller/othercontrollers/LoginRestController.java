package com.attendit.Attend.It.controller.othercontrollers;

import com.attendit.Attend.It.dto.LoginRequest;
import com.attendit.Attend.It.responses.Response;
import com.attendit.Attend.It.security.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class LoginRestController {
    private final AuthenticationService authenticationService;

    @Autowired
    public LoginRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest userLoginRequest) {
        return ResponseEntity.ok(authenticationService.login(userLoginRequest));
    }
}