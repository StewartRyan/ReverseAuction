package com.ryan.assignment2;

import com.ryan.assignment2.domain.entities.Bid;
import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.domain.entities.Role;
import com.ryan.assignment2.services.IBidService;
import com.ryan.assignment2.services.IJobService;
import com.ryan.assignment2.services.IMemberService;
import com.ryan.assignment2.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements ApplicationRunner
{
    @Autowired
    IRoleService _roleService;

    @Autowired
    IJobService _jobService;

    @Autowired
    IMemberService _memberService;

    @Autowired
    IBidService _bidService;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        Role standardRole = new Role();
        standardRole.setName("standard");

        _roleService.save(standardRole);

        Member newMember = new Member();
        newMember.setPassHash("a");
        newMember.setEmail("some_man@example.com");
        newMember.setName("John Doe");
        newMember.setPhoneNumber("0877654321");

        Member newMember2 = new Member();
        newMember2.setPassHash("a");
        newMember2.setEmail("a@a.a");
        newMember2.setName("Ryan S.");
        newMember2.setPhoneNumber("0871234567");

        Member newMember3 = new Member();
        newMember3.setPassHash("a");
        newMember3.setEmail("name@example.com");
        newMember3.setName("James Taylor");
        newMember3.setPhoneNumber("087000000");

        _memberService.registerUser(newMember, newMember2, newMember3);

        Job job1 = new Job();
        job1.setDate(LocalDate.now());
        job1.setName("Clean my house");
        job1.setDescription("I need someone to come to my house to give it a thorough clean.");
        job1.setMember(newMember);

        Job job2 = new Job();
        job2.setDate(LocalDate.now());
        job2.setName("Fix my bike please");
        job2.setDescription("My bike is broken, can you come to fix it?");
        job2.setMember(newMember2);

        Job job3 = new Job();
        job3.setDate(LocalDate.now());
        job3.setName("Fix my roof");
        job3.setDescription("My roof has a big hole in it.");
        job3.setMember(newMember2);
        job3.setState("expired");

        Job job4 = new Job();
        job4.setDate(LocalDate.now());
        job4.setName("Help me move rubbish");
        job4.setDescription("I need rubbish bins to be moved to the dump");
        job4.setMember(newMember3);

        _jobService.save(job1, job2, job3, job4);

        Bid newBid = new Bid();
        newBid.setJob(job3);
        newBid.setAmount(45.67f);
        newBid.setMember(newMember);

        Bid newBid2 = new Bid();
        newBid2.setJob(job3);
        newBid2.setAmount(32.52f);
        newBid2.setMember(newMember);

        _bidService.save(newBid, newBid2);
    }
}