package com.attendit.Attend.It.controller.dashboard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenValidationController {

    @PostMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Extract JWT token from the Authorization header
            String jwtToken = authorizationHeader.substring(7); // Remove "Bearer " prefix

            // Validate JWT token (e.g., check signature, expiration, etc.)
            // If validation succeeds, return HTTP 200 OK
            // Otherwise, throw an exception or return HTTP 401 Unauthorized

            return ResponseEntity.ok("Token is valid");
        } catch (Exception e) {
            // If an error occurs during token validation, return HTTP 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
    }
}
