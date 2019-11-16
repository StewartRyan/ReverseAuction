package com.ryan.assignment2.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class BidDetails
{
    private int bidId;
    private float amount;
    private Timestamp timestamp;
    private int jobId;
    private int memberId;
    private String memberEmail;
}
