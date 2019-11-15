package com.ryan.assignment2.services.Impl;

import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.domain.models.JobDetails;
import com.ryan.assignment2.repositories.IJobRepository;
import com.ryan.assignment2.repositories.IMemberRepository;
import com.ryan.assignment2.services.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService implements IJobService {

    @Autowired
    private IJobRepository _jobRepository;

    public List<JobDetails> getAllJobs() {
        return _jobRepository.findAll().stream()
                .map(j -> new JobDetails(
                    j.getName(),
                    j.getDescription(),
                    j.getDate(),
                    j.getJobId(),
                    j.getMember().getUserId()

        )).collect(Collectors.toList());
    }

    public void save(Job...jobs) {
        for (Job job : jobs)
            _jobRepository.save(job);
    }
}
