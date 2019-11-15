package com.ryan.assignment2;

import com.ryan.assignment2.domain.entities.Role;
import com.ryan.assignment2.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    IRoleService _roleService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role standardRole = new Role();
        standardRole.setName("standard");

        _roleService.save(standardRole);
    }
}