package com.ryan.assignment2.repositories;

import com.ryan.assignment2.domain.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobRepository extends JpaRepository<Job, Integer> {
}
