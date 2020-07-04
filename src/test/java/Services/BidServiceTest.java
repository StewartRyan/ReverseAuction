package Services;

import com.ryan.assignment2.domain.entities.Bid;
import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.domain.models.BidDetails;
import com.ryan.assignment2.repositories.IBidRepository;
import com.ryan.assignment2.services.Impl.BidService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BidServiceTest
{
    @Mock // mock account repository mocks the abstracted functionality
    IBidRepository _mockBidRepository;

    // Here we test a happy path scenario where everything completes successfully
    // and the owner is successfully added to an account
    @Test
    public void isBidValidSuccessTest()
    {
        when(_mockBidRepository.findByJob_JobId(0)).thenReturn(getMockBids());

        BidService bidService = new BidService(_mockBidRepository);
        boolean actual = bidService.isBidValid(0, 0);

        // We expect an empty string to be returned on success
        // Therefore we assert that the actual result is an empty string
        assertTrue(actual);
    }

    @Test
    public void isBidValidFailureTest()
    {
        when(_mockBidRepository.findByJob_JobId(0)).thenReturn(getMockBids());

        BidService bidService = new BidService(_mockBidRepository);
        boolean actual = bidService.isBidValid(0, 2);

        // We expect an empty string to be returned on success
        // Therefore we assert that the actual result is an empty string
        assertFalse(actual);
    }

    @Test
    public void saveBidSuccessTest()
    {
        BidService bidService = new BidService(_mockBidRepository);
        bidService.save(getMockBid());
    }

    @Test
    public void getBidsByMemberIdSuccessTest()
    {
        when(_mockBidRepository.findByMember_MemberId(0)).thenReturn(getMockBids());

        BidService bidService = new BidService(_mockBidRepository);
        List<Bid> actual = bidService.getBidsByMemberId(0);

        assertThat(actual, samePropertyValuesAs(getMockBids()));
    }

    @Test
    public void getBidsForJobSuccessTest()
    {
        when(_mockBidRepository.findByJob_JobId(0)).thenReturn(getMockBids());

        BidService bidService = new BidService(_mockBidRepository);
        List<BidDetails> actual = bidService.getBidsForJob(0);

        // We expect an empty string to be returned on success
        // Therefore we assert that the actual result is an empty string
        assertThat(actual, samePropertyValuesAs(getMockBidsDetails()));
    }

    @Test
    public void getBidsForJobFailureTest()
    {
        when(_mockBidRepository.findByJob_JobId(0)).thenReturn(new ArrayList<>());

        BidService bidService = new BidService(_mockBidRepository);
        List<BidDetails> actual = bidService.getBidsForJob(0);

        // We expect an empty string to be returned on success
        // Therefore we assert that the actual result is an empty string
        assertTrue(actual.isEmpty());
    }

    private Bid getMockBid()
    {
        return new Bid(
                0,
                1,
                null,
                getMockMember(),
                getMockJob()
        );
    }

    private Member getMockMember()
    {
        Member member = new Member();
        member.setEmail("test");
        member.setName("test");
        member.setMemberId(0);

        return member;
    }

    private Job getMockJob()
    {
        Job job = new Job();
        job.setJobId(0);

        return job;
    }

    private BidDetails getMockBidDetails()
    {
        return new BidDetails(
                0,
                1,
                null,
                0,
                0,
                "test",
                "test"
        );
    }



    // Mocked Owner object, we'll use this for parameters requiring Owner objects
    private List<Bid> getMockBids()
    {
        List<Bid> list = new ArrayList<>();
        list.add(getMockBid());

        return list;
    }

    // Mocked Owner object, we'll use this for parameters requiring Owner objects
    private List<BidDetails> getMockBidsDetails()
    {
        List<BidDetails> list = new ArrayList<>();
        list.add(getMockBidDetails());

        return list;
    }
}
