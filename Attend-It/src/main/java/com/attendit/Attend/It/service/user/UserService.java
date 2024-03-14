package com.attendit.Attend.It.service.user;

import com.attendit.Attend.It.entities.user.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
    User findUserById(int id);
    void deleteUserById(int id);
    User findUserByUsername(String username);
    User findUserByUsernameAndPassword(String username, String password);
    User findUserByEmail(String email);
    List<User> findAll(int pageNumber);
}
