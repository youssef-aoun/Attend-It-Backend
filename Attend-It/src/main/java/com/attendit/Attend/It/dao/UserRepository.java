package com.attendit.Attend.It.dao;

import com.attendit.Attend.It.entities.roles.Role;
import com.attendit.Attend.It.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByIdAndRoles(int id, Set<Role> roles);
    User findByUsername(String username);
    User findUserByUsernameAndPassword(String username, String password);
    User findUserByEmail(String email);
    Page<User> findUsersByRoles(Set<Role> roles, Pageable pageable);
}
