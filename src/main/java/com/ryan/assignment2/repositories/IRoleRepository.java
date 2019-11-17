package com.ryan.assignment2.repositories;

import com.ryan.assignment2.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Integer> {}
