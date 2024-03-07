package com.attendit.Attend.It.controller.entities;

import com.attendit.Attend.It.dto.SignupRequest;
import com.attendit.Attend.It.service.user.UserService;
import com.attendit.Attend.It.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> users(){
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable int userId){
        User user = userService.findUserById(userId);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User theUser){
        theUser.setId(0);
        return userService.save(theUser);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        return userService.save(user);
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId){
        User user = userService.findUserById(userId);
        if(user == null){
            return "User with ID " + userId + " not found!";
        }
        else {
            userService.deleteUserById(userId);
            return "Deleted!";
        }
    }

    @PostMapping("/users/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {

        if(userService.findUserByUsername(signupRequest.getUsername()) == null &&
        userService.findUserByEmail(signupRequest.getEmail()) == null) {

            String username = signupRequest.getUsername();
            String password = signupRequest.getPassword();
            String firstName = signupRequest.getFirstName();
            String lastName = signupRequest.getLastName();
            String email = signupRequest.getEmail();

            // Create a new user object with the extracted details
            User newUser = new User(firstName, lastName, email, password);
            newUser.setUsername(username);

            // Save the new user in the database
            userService.save(newUser);

            // Return a success response
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
        else if(userService.findUserByUsername(signupRequest.getUsername()) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is taken.");
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is taken.");
        }
    }



}
