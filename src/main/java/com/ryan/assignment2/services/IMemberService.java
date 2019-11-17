package com.ryan.assignment2.services;

import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.domain.models.MemberDetails;

public interface IMemberService
{
    Member findByUsername(String email);
    MemberDetails getCurrentMemberDetails();
    String registerUser(Member...newMember);
    Member getCurrentMember();
}

