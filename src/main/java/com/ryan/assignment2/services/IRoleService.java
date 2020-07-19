package com.ryan.assignment2.services;

import com.ryan.assignment2.domain.entities.Role;

public interface IRoleService
{
    Role insertRoles();
    Role save(Role role);
}
