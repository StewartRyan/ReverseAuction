package com.ryan.assignment2.repositories;

import com.ryan.assignment2.domain.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IJobRepository extends JpaRepository<Job, Integer>
{
    List<Job> findByTimestampBefore(Date expiryDate);
    List<Job> findByState(String state);
}
