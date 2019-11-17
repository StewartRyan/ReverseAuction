package com.ryan.assignment2.services.Impl;

import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.domain.entities.Role;
import com.ryan.assignment2.domain.models.MemberDetails;
import com.ryan.assignment2.repositories.IMemberRepository;
import com.ryan.assignment2.repositories.IRoleRepository;
import com.ryan.assignment2.services.IMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class MemberService implements IMemberService
{
    @Autowired
    private IMemberRepository _userRepository;

    @Autowired
    private IRoleRepository _roleRepository;

    @Autowired
    private PasswordEncoder _passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public String registerUser(Member...members)
    {
        HashSet<Role> roles = new HashSet<>(_roleRepository.findAll());

        for (Member newMember: members)
        {
            newMember.setRoles(roles);
            newMember.setPassHash(
                    _passwordEncoder.encode(newMember.getPassHash())
            );

            _userRepository.save(newMember);
        }

        return "";
    }

    public Member findByUsername(String email)
    {
        return _userRepository.findByEmail(email);
    }

    public MemberDetails getCurrentMemberDetails()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User)
        {
            String email = ((User) principal).getUsername();
            Member member = _userRepository.findByEmail(email);

            return new MemberDetails(
                    member.getEmail(),
                    member.getName(),
                    member.getPhoneNumber(),
                    member.getMemberId()
            );
        }

        return null;
    }

    public Member getCurrentMember()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User)
        {
            String email = ((User) principal).getUsername();
            return _userRepository.findByEmail(email);
        }

        return null;
    }
}
