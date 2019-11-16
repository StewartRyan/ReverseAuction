package com.ryan.assignment2.services;

import com.ryan.assignment2.domain.entities.Bid;
import com.ryan.assignment2.domain.models.BidDetails;

import java.util.List;

public interface IBidService {
    void save(Bid...bids);
    boolean isBidValid(int jobId, float amount);
    List<BidDetails> getBidsForJob(int jobId);
}
