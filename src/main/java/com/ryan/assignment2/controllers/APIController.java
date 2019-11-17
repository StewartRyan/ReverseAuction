package com.ryan.assignment2.controllers;

import com.ryan.assignment2.domain.entities.Bid;
import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.services.IBidService;
import com.ryan.assignment2.services.IJobService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    private IJobService _jobService;

    @Autowired
    private IBidService _bidService;

    @GetMapping("getActiveJobs")
    public List<Job> getAllActiveJobs()
    {
        return _jobService.getAllActiveJobs();
    }

    @GetMapping("getBidsByMemberId")
    public List<Bid> getBidsByMemberId(@RequestParam Map<String, String> queryParameters)
    {
        try
        {
           int mid = Integer.parseInt(queryParameters.get("mid"));
           return _bidService.getBidsByMemberId(mid);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
