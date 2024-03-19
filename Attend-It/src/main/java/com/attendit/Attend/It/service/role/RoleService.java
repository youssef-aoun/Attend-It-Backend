package com.attendit.Attend.It.service.role;

import com.attendit.Attend.It.entities.roles.Role;

import java.util.List;

public interface RoleService {
    Role save(Role role);
    List<Role> findAll();
    Role findRoleById(int id);
    Role findRoleByName(String name);
}
