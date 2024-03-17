package com.attendit.Attend.It.dao;

import com.attendit.Attend.It.entities.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
