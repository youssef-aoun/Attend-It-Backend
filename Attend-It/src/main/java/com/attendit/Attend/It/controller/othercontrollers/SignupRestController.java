
package com.attendit.Attend.It.controller.othercontrollers;

import com.attendit.Attend.It.dto.SignupRequest;
import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.responses.Response;
import com.attendit.Attend.It.security.authentication.AuthenticationService;
import com.attendit.Attend.It.service.role.RoleService;
import com.attendit.Attend.It.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class SignupRestController {

    private final UserService userService;
    private final AuthenticationService authenticationService;


    @Autowired
    public SignupRestController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/users/signup")
    public ResponseEntity<Response> signupUser(@RequestBody SignupRequest signupRequest) {
        if(userService.findUserByUsername(signupRequest.getUsername()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(signupResponseFailure("Username is taken."));
        else if(userService.findUserByEmail(signupRequest.getEmail()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(signupResponseFailure("Email is taken."));
        else{
            Response response = authenticationService.signup(signupRequest);
            // Return a success response
            return ResponseEntity.ok(response);
        }
    }


    private Response signupResponseFailure(String message){
        Response signupResponse = new Response();
        signupResponse.setMessage(message);
        signupResponse.setStatus(409);
        return signupResponse;
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

