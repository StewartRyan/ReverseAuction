package com.ryan.assignment2.repositories;

import com.ryan.assignment2.domain.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBidRepository extends JpaRepository<Bid, Integer>
{
    List<Bid> findByJob_JobId(int jobId);
    List<Bid> findByMember_MemberId(int memberId);
}
