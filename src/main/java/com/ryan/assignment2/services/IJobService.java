package com.ryan.assignment2.services;

import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.domain.models.JobDetails;

import java.util.List;

public interface IJobService {
    List<JobDetails> getAllJobs();
    void save(Job...job);
}
