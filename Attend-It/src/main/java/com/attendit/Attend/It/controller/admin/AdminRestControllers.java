package com.attendit.Attend.It.controller.admin;

import com.attendit.Attend.It.entities.user.User;
import com.attendit.Attend.It.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminRestControllers {

    private final UserService userService;

    @Autowired
    public AdminRestControllers(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/verify/{userId}")
    public User verifyUser(@PathVariable int userId){
        User user = userService.findUserById(userId, "ROLE_USER");
        user.setVerified(true);
        userService.save(user);
        return user;
    }
}
