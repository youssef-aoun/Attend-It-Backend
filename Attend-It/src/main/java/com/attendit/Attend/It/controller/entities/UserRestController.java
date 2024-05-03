package com.attendit.Attend.It.controller.entities;

import com.attendit.Attend.It.dto.EditUserRequest;
import com.attendit.Attend.It.dto.EventUpdateRequest;
import com.attendit.Attend.It.entities.roles.Role;
import com.attendit.Attend.It.service.role.RoleService;
import com.attendit.Attend.It.service.user.UserService;
import com.attendit.Attend.It.entities.user.User;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public List<User> users(@RequestParam(name = "page", defaultValue = "0") int pageNumber,
                            @RequestParam(name = "role", defaultValue = "ROLE_USER") String role){
        System.out.println(role);
        return userService.findUsersByRole(role, pageNumber);
    }

    @GetMapping("/all-users")
    public List<User> users(){
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId){
        User user = userService.findUserById(userId, "ROLE_USER");
        if(user == null){
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @PostMapping("")
    public User addUser(@RequestBody User theUser){
        theUser.setId(0);
        return userService.save(theUser);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable int userId, @RequestBody EditUserRequest editUserRequest){
        User userToUpdate = userService.findUserById(userId, "ROLE_USER");
        Role role = roleService.findRoleByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        editUserRequest.getUser().setRoles(roles);
        User updatedUser = editUserRequest.getUser();
        updatedUser.setUsername(userToUpdate.getUsername());
        updatedUser.setPassword(userToUpdate.getPassword());
        if(updatedUser.getId() == 0)
            updatedUser.setId(userId);
        BeanUtils.copyProperties(updatedUser, userToUpdate, getNullPropertyNames(updatedUser));
        return userService.save(userToUpdate);
    }



    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable int userId){
        User user = userService.findUserById(userId, "ROLE_USER");

        if(user == null){
            return "User with ID " + userId + " not found!";
        }
        else {
            userService.deleteUserById(userId);
            return "Deleted!";
        }
    }


    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


}
