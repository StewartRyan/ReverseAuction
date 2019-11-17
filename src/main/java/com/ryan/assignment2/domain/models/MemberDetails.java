package com.ryan.assignment2.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDetails
{
    private String email;
    private String name;
    private String phone;
    private int memberId;
}
