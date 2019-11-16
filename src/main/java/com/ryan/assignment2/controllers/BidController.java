package com.ryan.assignment2.controllers;

import com.ryan.assignment2.domain.entities.Bid;
import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.domain.models.BidDetails;
import com.ryan.assignment2.domain.models.JobDetails;
import com.ryan.assignment2.domain.models.MemberDetails;
import com.ryan.assignment2.services.IBidService;
import com.ryan.assignment2.services.IJobService;
import com.ryan.assignment2.services.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class BidController {
    @Autowired
    private IBidService _bidService;

    @Autowired
    private IMemberService _memberService;

    @Autowired
    private IJobService _jobService;

    @PostMapping("/placeBid")
    public String placeBid(@RequestParam Map<String, String> bidDetails, Model model)
    {
        MemberDetails member = _memberService.getCurrentMemberDetails();
        if (member == null)
        {
            return "redirect:/login";
        }

        // Validate user input
        String errorMessage = checkBidValues(bidDetails);

        // Only if no error is present, add new bid to DB
        if ("".equals(errorMessage))
        {
            // Get associated job with this bid
            int jobId = Integer.parseInt(bidDetails.get("jobId"));
            Job associatedJob = _jobService.getJob(jobId);
            if (associatedJob == null)
            {
                return "redirect:/error";
            }

            // Validate the bid
            float amount = Float.parseFloat(bidDetails.get("amount"));
            boolean isBidValid = _bidService.isBidValid(associatedJob.getJobId(), amount);

            if (isBidValid)
            {
                Bid newBid = new Bid();
                newBid.setMember(_memberService.getCurrentMember());
                newBid.setAmount(amount);
                newBid.setJob(associatedJob);

                _bidService.save(newBid);
            }
            else
            {
                errorMessage = "insufficient_amount_error";
            }
        }

        return "redirect:/placeBid/" + bidDetails.get("jobId")
                + ("".equals(errorMessage) ? "" : ("?error=" + errorMessage));
    }

    @GetMapping(path="/placeBid/{jobId}")
    public String placeBidPage(Model model, @PathVariable("jobId") int jobId, @RequestParam Map<String, String> queryParameters)
    {
        MemberDetails member = _memberService.getCurrentMemberDetails();
        if (member == null)
        {
            return "redirect:/login";
        }

        // Get associated job with this bid
        Job associatedJob = _jobService.getJob(jobId);
        if (associatedJob == null)
        {
            return "redirect:/";
        }
        else
        {
            JobDetails jobDetails = _jobService.getJobDetails(jobId);
            List<BidDetails> bids = _bidService.getBidsForJob(jobId);

            model.addAttribute("bids", bids);
            model.addAttribute("current_member", member);
            model.addAttribute("job", jobDetails);
        }

        if (!"".equals(queryParameters.get("error")))
        {
            model.addAttribute("error", queryParameters.get("error"));
        }

        return "placeBid";
    }

    private String checkBidValues(Map<String, String> bid)
    {
        if ("".equals(bid.get("amount")))
        {
            return "invalid_amount_error";
        }

        try
        {
            float amount = Float.parseFloat(bid.get("amount"));

            if (amount < 0.01)
            {
                return "invalid_amount_error";
            }
        }
        catch (NumberFormatException e)
        {
            return "invalid_amount_error";
        }

        try
        {
            Integer.parseInt(bid.get("jobId"));
        }
        catch (NumberFormatException e)
        {
            return "invalid_jobId_error";
        }

        return "";
    }
}
