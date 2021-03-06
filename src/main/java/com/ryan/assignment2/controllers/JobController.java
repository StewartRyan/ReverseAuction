package com.ryan.assignment2.controllers;

import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.domain.models.JobDetails;
import com.ryan.assignment2.domain.models.MemberDetails;
import com.ryan.assignment2.services.IJobService;
import com.ryan.assignment2.services.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class JobController
{
    @Autowired
    private IMemberService _userService;

    @Autowired
    private IJobService _jobService;

    @GetMapping(value= {"/"})
    public String handleIndexRequest(Model model)
    {
        List<JobDetails> jobs = _jobService.getAllJobs();
        if (jobs.size() > 0)
        {
            model.addAttribute("all_jobs", jobs);
        }

        MemberDetails member = _userService.getCurrentMemberDetails();
        if (member != null)
        {
            model.addAttribute("current_member", member);
            return "jobs";
        }

        return "redirect:/login";
    }


    @PostMapping("/newJob")
    public String addNewJob(@RequestParam Map<String, String> jobDetails, Model model)
    {
        MemberDetails member = _userService.getCurrentMemberDetails();
        if (member == null)
        {
            return "redirect:/login";
        }

        // Validate user input
        String returnMessage = checkJobValues(jobDetails);

        // Only if no error is present, add new job to DB
        if ("".equals(returnMessage))
        {
            Job newJob = new Job();
            newJob.setName(jobDetails.get("name"));
            newJob.setDescription(jobDetails.get("description"));
            newJob.setDate(LocalDate.now());
            newJob.setMember(_userService.getCurrentMember());

            _jobService.save(newJob);
            returnMessage = "success";
        }

        return "redirect:/newJob?message=" + returnMessage;
    }


    @GetMapping("/newJob")
    public String newJobPage(Model model, @RequestParam Map<String, String> queryParameters)
    {
        MemberDetails member = _userService.getCurrentMemberDetails();
        if (member == null)
        {
            return "redirect:/login";
        }

        if (!"".equals(queryParameters.get("message")))
        {
            model.addAttribute("error", queryParameters.get("message"));
        }

        model.addAttribute("current_member", member);


        return "newJob";
    }

    @Scheduled(fixedRate = 60000)
    public void updateJobStates()
    {
        _jobService.updateJobStates();
    }

    private String checkJobValues(Map<String, String> job)
    {
        if ("".equals(job.get("name")))
            return "name_error";

        if ("".equals(job.get("description")))
            return "description_error";

        return "";
    }
}