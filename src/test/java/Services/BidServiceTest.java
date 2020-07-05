package Services;

import com.ryan.assignment2.domain.entities.Bid;
import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.domain.models.BidDetails;
import com.ryan.assignment2.repositories.IBidRepository;
import com.ryan.assignment2.services.Impl.BidService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BidServiceTest
{
    @Mock
    IBidRepository _mockBidRepository;

    /************************ IS BID VALID TESTS *********************************/
    @Test
    public void isBidValidSuccessTest()
    {
        when(_mockBidRepository.findByJob_JobId(0)).thenReturn(getMockBids());

        BidService bidService = new BidService(_mockBidRepository);
        boolean actual = bidService.isBidValid(0, 0);

        assertTrue(actual);
    }

    @Test
    public void isBidValidFailureTest()
    {
        when(_mockBidRepository.findByJob_JobId(0)).thenReturn(getMockBids());

        BidService bidService = new BidService(_mockBidRepository);
        boolean actual = bidService.isBidValid(0, 2);

        assertFalse(actual);
    }

    /************************ SAVE BID TESTS *********************************/

    @Test
    public void saveBidSuccessTest()
    {
        BidService bidService = new BidService(_mockBidRepository);
        bidService.save(getMockBid());
    }

    /************************ GET BIDS BY MEMBER ID TESTS *********************************/

    @Test
    public void getBidsByMemberIdSuccessTest()
    {
        when(_mockBidRepository.findByMember_MemberId(0)).thenReturn(getMockBids());

        BidService bidService = new BidService(_mockBidRepository);
        List<Bid> actual = bidService.getBidsByMemberId(0);

        assertThat(actual, samePropertyValuesAs(getMockBids()));
    }

    /************************ GET BIDS FOR JOB TESTS *********************************/

    @Test
    public void getBidsForJobSuccessTest()
    {
        when(_mockBidRepository.findByJob_JobId(0)).thenReturn(getMockBids());

        BidService bidService = new BidService(_mockBidRepository);
        List<BidDetails> actual = bidService.getBidsForJob(0);

        assertThat(actual, samePropertyValuesAs(getMockBidsDetails()));
    }

    @Test
    public void getBidsForJobFailureTest()
    {
        when(_mockBidRepository.findByJob_JobId(0)).thenReturn(new ArrayList<>());

        BidService bidService = new BidService(_mockBidRepository);
        List<BidDetails> actual = bidService.getBidsForJob(0);

        assertTrue(actual.isEmpty());
    }

    /************************ HELPER METHODS *********************************/

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

    private List<Bid> getMockBids()
    {
        List<Bid> list = new ArrayList<>();
        list.add(getMockBid());

        return list;
    }

    private List<BidDetails> getMockBidsDetails()
    {
        List<BidDetails> list = new ArrayList<>();
        list.add(getMockBidDetails());

        return list;
    }
}
