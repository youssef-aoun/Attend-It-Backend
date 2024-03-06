package com.attendit.Attend.It.service;

import com.attendit.Attend.It.entities.user.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
    User findUserById(int id);
    void deleteUserById(int id);
}
