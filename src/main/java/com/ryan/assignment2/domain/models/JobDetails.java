package com.ryan.assignment2.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class JobDetails {
    private String name;
    private String description;
    private LocalDate date;
    private int jobId;
    private int memberId;
}