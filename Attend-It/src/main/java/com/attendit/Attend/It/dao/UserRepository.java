package com.attendit.Attend.It.dao;

import com.attendit.Attend.It.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
