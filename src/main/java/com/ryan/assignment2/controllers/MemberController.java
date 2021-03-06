package com.ryan.assignment2.controllers;

import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.services.ISecurityService;
import com.ryan.assignment2.services.IMemberService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Slf4j
@Controller
public class MemberController
{
    @Autowired
    private IMemberService _memberService;

    @Autowired
    private ISecurityService _securityService;

    @PostMapping("/register")
    public String registerUser(@RequestParam Map<String, String> user)
    {
        // Validate user input
        String returnMessage = checkUserValues(user);

        // Only if no error is present, add new user to DB
        if ("".equals(returnMessage))
        {
            // Create the new user
            Member newMember = new Member();
            newMember.setEmail(user.get("email"));
            newMember.setName(user.get("name"));
            newMember.setPassHash(user.get("password"));
            newMember.setPhoneNumber(user.get("phone"));

            returnMessage = _memberService.registerUser(newMember);
            if ("success".equals(returnMessage))
            {
                _securityService.autoLogin(user.get("email"), user.get("password"));
            }
        }

        return "redirect:/register?message=" + returnMessage;
    }

    @GetMapping("/register")
    public String registerPage(@RequestParam Map<String, String> queryParameters, Model model)
    {
        if (!"".equals(queryParameters.get("message")))
        {
            model.addAttribute("error", queryParameters.get("message"));
        }

        if ("login".equals(queryParameters.get("ref")))
        {
            return "redirect:/login";
        }

        return "register";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout)
    {

        if (error != null)
            model.addAttribute("error", "login_error");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    private String checkUserValues(Map<String, String> user)
    {
        if ("".equals(user.get("email")))
            return "email_error";

        if ("".equals(user.get("name")))
            return "name_error";

        if ("".equals(user.get("password")))
            return "password_error";

        if ("".equals(user.get("phone")) || !user.get("phone").matches("^[0-9]*$"))
            return "phone_error";

        return "";
    }
}
