package com.ryan.assignment2.repositories;

import com.ryan.assignment2.domain.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMemberRepository extends JpaRepository<Member, Integer>
{
    Member findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);
}
