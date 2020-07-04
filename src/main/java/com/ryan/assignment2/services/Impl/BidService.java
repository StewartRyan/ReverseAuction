package com.ryan.assignment2.services.Impl;

import com.ryan.assignment2.domain.entities.Bid;
import com.ryan.assignment2.domain.models.BidDetails;
import com.ryan.assignment2.repositories.IBidRepository;
import com.ryan.assignment2.services.IBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidService implements IBidService
{
    @Autowired
    private IBidRepository _bidRepository;

    public BidService(IBidRepository bidRepository)
    {
        _bidRepository = bidRepository;
    }

    public void save(Bid...bids)
    {
        for (Bid bid: bids)
            _bidRepository.save(bid);
    }

    public boolean isBidValid(int jobId, float amount)
    {
        List<Bid> bids = _bidRepository.findByJob_JobId(jobId);

        // Check for any bids on this job that have a higher amount than
        // current user is trying to bid
        return bids.stream().noneMatch(b -> amount >= b.getAmount());
    }

    @Override
    public List<BidDetails> getBidsForJob(int jobId)
    {
        List<Bid> bids = _bidRepository.findByJob_JobId(jobId);
        // Get all jobs from the DB and translate them into JobDetails objects
        return bids.stream()
                .map(b -> new BidDetails(
                        b.getBidId(),
                        b.getAmount(),
                        b.getTimestamp(),
                        b.getJob().getJobId(),
                        b.getMember().getMemberId(),
                        b.getMember().getEmail(),
                        b.getMember().getName()

                )).collect(Collectors.toList());
    }

    @Override
    public List<Bid> getBidsByMemberId(int memberId) {
        return _bidRepository.findByMember_MemberId(memberId);
    }
}
