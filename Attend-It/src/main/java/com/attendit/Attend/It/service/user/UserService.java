package com.attendit.Attend.It.service.user;

import com.attendit.Attend.It.entities.event.Event;
import com.attendit.Attend.It.entities.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
    void deleteUserById(int id);

    User findUserById(int id, String roleName);

    User findUserByUsername(String username);
    User findUserByUsernameAndPassword(String username, String password);
    User findUserByEmail(String email);
    List<User> findAll(int pageNumber);
    List<User> findUsersByRole(String roleName, int pageNumber);

}
