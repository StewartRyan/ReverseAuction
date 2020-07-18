package Services;

import com.ryan.assignment2.domain.entities.Bid;
import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.domain.models.BidDetails;
import com.ryan.assignment2.domain.models.JobDetails;
import com.ryan.assignment2.repositories.IJobRepository;
import com.ryan.assignment2.services.Impl.BidService;
import com.ryan.assignment2.services.Impl.JobService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class JobServiceTest
{
    @Mock
    IJobRepository _mockJobRepository;

    static Job mockJob = new Job();

    private static boolean initComplete = false;

    @Before
    public void init()
    {
        if (initComplete) return;

        mockJob.setJobId(0);
        mockJob.setName("test");
        mockJob.setState("test");
        mockJob.setMember(getMockMember());
        mockJob.setDescription("test");

        initComplete = true;
    }

    /************************ IS BID VALID TESTS *********************************/
    @Test
    public void getJobSuccessTest()
    {
        when(_mockJobRepository.findById(any(int.class))).thenReturn(Optional.of(mockJob));

        JobService jobService = new JobService(_mockJobRepository);
        Job actual = jobService.getJob(0);

        assertThat(actual, samePropertyValuesAs(mockJob));
    }

    @Test
    public void getJobJobIsNullTest()
    {
        when(_mockJobRepository.findById(any(int.class))).thenReturn(Optional.empty());

        JobService jobService = new JobService(_mockJobRepository);
        Job actual = jobService.getJob(0);

        assertNull(actual);
    }

    @Test
    public void getJobDetailsSuccessTest()
    {
        when(_mockJobRepository.findById(any(int.class))).thenReturn(Optional.of(mockJob));

        JobService jobService = new JobService(_mockJobRepository);
        JobDetails actual = jobService.getJobDetails(0);

        assertThat(actual, samePropertyValuesAs(getMockJobDetails()));
    }

    @Test
    public void getJobDetailsNoJobFoundTest()
    {
        when(_mockJobRepository.findById(any(int.class))).thenReturn(Optional.empty());

        JobService jobService = new JobService(_mockJobRepository);
        JobDetails actual = jobService.getJobDetails(0);

        assertNull(actual);
    }

    @Test
    public void getAllJobsSuccessTest()
    {
        when(_mockJobRepository.findAll()).thenReturn(getMockJobs());

        JobService jobService = new JobService(_mockJobRepository);
        List<JobDetails> actual = jobService.getAllJobs();

        assertThat(actual, samePropertyValuesAs(getMockJobsDetails()));
    }

    @Test
    public void saveJobSuccessTest()
    {
        JobService jobService = new JobService(_mockJobRepository);
        jobService.save(mockJob);
    }

    @Test
    public void updateJobStatesSuccessTest()
    {
        when(_mockJobRepository.findByTimestampBefore(any(Date.class))).thenReturn(getMockJobs());
        JobService jobService = new JobService(_mockJobRepository);
        jobService.updateJobStates();
    }

    @Test
    public void getAllActiveJobsSuccessTest()
    {
        when(_mockJobRepository.findByState(any(String.class))).thenReturn(getMockJobs());

        JobService jobService = new JobService(_mockJobRepository);
        List<Job> actual = jobService.getAllActiveJobs();

        assertThat(actual, samePropertyValuesAs(getMockJobs()));
    }

    /************************ HELPER METHODS *********************************/

    private Member getMockMember()
    {
        Member member = new Member();
        member.setEmail("test");
        member.setName("test");
        member.setMemberId(0);

        return member;
    }


    private JobDetails getMockJobDetails()
    {
        return new JobDetails(
                "test",
                "test",
                mockJob.getDate(),
                0,
                0,
                "test",
                "test",
                "test"
        );
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

    private List<Job> getMockJobs()
    {
        List<Job> list = new ArrayList<>();
        list.add(mockJob);

        return list;
    }

    private List<JobDetails> getMockJobsDetails()
    {
        List<JobDetails> list = new ArrayList<>();
        list.add(getMockJobDetails());

        return list;
    }
}
