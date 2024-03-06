package com.attendit.Attend.It.controller;

import com.attendit.Attend.It.service.user.UserService;
import com.attendit.Attend.It.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
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

}
