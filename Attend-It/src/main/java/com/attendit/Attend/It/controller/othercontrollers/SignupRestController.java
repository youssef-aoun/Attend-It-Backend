
package com.attendit.Attend.It.controller.othercontrollers;

import com.attendit.Attend.It.dto.SignupRequest;
import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.errorresponses.SignupErrorResponse;
import com.attendit.Attend.It.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class SignupRestController {

    private final UserService userService;

    @Autowired
    public SignupRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if(userService.findUserByUsername(signupRequest.getUsername()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SignupErrorResponse("Username is taken."));
        else if(userService.findUserByEmail(signupRequest.getEmail()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SignupErrorResponse("Email is taken."));
        else{
            User newUser = getUser(signupRequest);
            userService.save(newUser);
            // Return a success response
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
    }

    private static User getUser(SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();
        String firstName = signupRequest.getFirstName();
        String lastName = signupRequest.getLastName();
        String email = signupRequest.getEmail();

        // Create a new user object with the extracted details
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        return newUser;
    }

}

