package com.ryan.assignment2.controllers;

import com.ryan.assignment2.domain.models.MemberDetails;
import com.ryan.assignment2.services.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobController {
    @Autowired
    private IMemberService _userService;

    @GetMapping(value= { "/"})
    public String handleIndexRequest(Model model)
    {
        MemberDetails member = _userService.getCurrentMemberDetails();
        if (member != null)
        {
            model.addAttribute("current_member", member);
            return "jobs";
        }

        return "redirect:/login";
    }
}