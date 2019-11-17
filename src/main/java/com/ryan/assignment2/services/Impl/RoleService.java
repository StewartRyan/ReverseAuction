package com.ryan.assignment2.services.Impl;

import com.ryan.assignment2.domain.entities.Role;
import com.ryan.assignment2.repositories.IRoleRepository;
import com.ryan.assignment2.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService
{

    @Autowired
    private IRoleRepository _roleRepository;

    public void insertRoles()
    {
        Role userRole = new Role();
        userRole.setName("standard");
        _roleRepository.save(userRole);
    }

    @Override
    public void save(Role role)
    {
        _roleRepository.save(role);
    }
}
