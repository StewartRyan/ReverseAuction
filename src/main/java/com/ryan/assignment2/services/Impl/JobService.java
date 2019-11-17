package com.ryan.assignment2.services.Impl;

import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.domain.models.JobDetails;
import com.ryan.assignment2.repositories.IJobRepository;
import com.ryan.assignment2.services.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService implements IJobService
{
    @Autowired
    private IJobRepository _jobRepository;

    public Job getJob(int jobId)
    {
        Optional<Job> job = _jobRepository.findById(jobId);

        return job.orElse(null);
    }

    public JobDetails getJobDetails(int jobId)
    {
        Optional<Job> jobOptional = _jobRepository.findById(jobId);
        if (jobOptional.isPresent())
        {
            Job job = jobOptional.get();
            return new JobDetails(
                    job.getName(),
                    job.getDescription(),
                    job.getDate(),
                    job.getJobId(),
                    job.getMember().getMemberId(),
                    job.getMember().getEmail(),
                    job.getState()
            );
        }

        return null;
    }

    public List<JobDetails> getAllJobs()
    {
        // Get all jobs from the DB and translate them into JobDetails objects
        return _jobRepository.findAll().stream()
                .map(j -> new JobDetails(
                    j.getName(),
                    j.getDescription(),
                    j.getDate(),
                    j.getJobId(),
                    j.getMember().getMemberId(),
                    j.getMember().getEmail(),
                    j.getState()

        )).collect(Collectors.toList());
    }

    public void save(Job...jobs)
    {
        for (Job job : jobs)
            _jobRepository.save(job);
    }

    public void updateJobStates()
    {
        // Calculate 20 days
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -20);

        Date expiredDate = calendar.getTime();

        List<Job> expiredJobs = _jobRepository.findByTimestampBefore(expiredDate);
        for (Job job: expiredJobs)
        {
            job.setState("expired");
            _jobRepository.save(job);
        }
    }

    @Override
    public List<Job> getAllActiveJobs() {
        return _jobRepository.findByState("active");
    }
}
