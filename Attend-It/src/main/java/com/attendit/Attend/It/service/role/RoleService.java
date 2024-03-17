package com.attendit.Attend.It.service.role;

import com.attendit.Attend.It.entities.roles.Role;

public interface RoleService {
    Role findRoleByName(String name);
}
