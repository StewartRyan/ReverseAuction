package Services;

import com.ryan.assignment2.domain.entities.Bid;
import com.ryan.assignment2.domain.entities.Job;
import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.domain.entities.Role;
import com.ryan.assignment2.domain.models.BidDetails;
import com.ryan.assignment2.domain.models.MemberDetails;
import com.ryan.assignment2.repositories.IBidRepository;
import com.ryan.assignment2.repositories.IMemberRepository;
import com.ryan.assignment2.repositories.IRoleRepository;
import com.ryan.assignment2.services.Impl.BidService;
import com.ryan.assignment2.services.Impl.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MemberServiceTest
{
    @Mock
    IMemberRepository _mockMemberRepository;

    @Mock
    IRoleRepository _mockRoleRepository;

    @Mock
    PasswordEncoder _mockPasswordEncoder;

    /************************ REGISTER USER TESTS *********************************/
    @Test
    public void registerUserSuccessTest()
    {
        when(_mockRoleRepository.findAll()).thenReturn(getMockRoles());
        when(_mockPasswordEncoder.encode(any(String.class))).thenReturn("test");
        when(_mockMemberRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(_mockMemberRepository.existsByPhoneNumber(any(String.class))).thenReturn(false);
        when(_mockMemberRepository.save(getMockMember())).thenReturn(getMockMember());

        MemberService memberService = new MemberService(_mockMemberRepository, _mockRoleRepository, _mockPasswordEncoder);
        String actual = memberService.registerUser(getMockMember());

        assertEquals("success", actual);
    }

    @Test
    public void registerUserEmailTakenTest()
    {
        when(_mockRoleRepository.findAll()).thenReturn(getMockRoles());
        when(_mockPasswordEncoder.encode(any(String.class))).thenReturn("test");
        when(_mockMemberRepository.existsByEmail(any(String.class))).thenReturn(true);

        MemberService memberService = new MemberService(_mockMemberRepository, _mockRoleRepository, _mockPasswordEncoder);
        String actual = memberService.registerUser(getMockMember());

        assertEquals("email_taken", actual);
    }

    @Test
    public void registerUserPhoneNumberTakenTest()
    {
        when(_mockRoleRepository.findAll()).thenReturn(getMockRoles());
        when(_mockPasswordEncoder.encode(any(String.class))).thenReturn("test");
        when(_mockMemberRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(_mockMemberRepository.existsByPhoneNumber(any(String.class))).thenReturn(true);

        MemberService memberService = new MemberService(_mockMemberRepository, _mockRoleRepository, _mockPasswordEncoder);
        String actual = memberService.registerUser(getMockMember());

        assertEquals("phone_taken", actual);
    }

    /************************ FIND BY USERNAME TESTS *********************************/

    @Test
    public void findByUsernameSuccessTest()
    {
        when(_mockMemberRepository.findByEmail(any(String.class))).thenReturn(getMockMember());

        MemberService memberService = new MemberService(_mockMemberRepository, _mockRoleRepository, _mockPasswordEncoder);
        Member actual = memberService.findByUsername("test");

        assertThat(actual, samePropertyValuesAs(getMockMember()));
    }

    /************************ GET CURRENT MEMBER DETAILS TESTS *********************************/

    @Test
    public void getCurrentMemberDetailsSuccessTest()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getPrincipal()).thenReturn(getMockUser());
        when(_mockMemberRepository.findByEmail(any(String.class))).thenReturn(getMockMember());

        MemberService memberService = new MemberService(_mockMemberRepository, _mockRoleRepository, _mockPasswordEncoder);
        MemberDetails actual = memberService.getCurrentMemberDetails();

        assertThat(actual, samePropertyValuesAs(getMockMemberDetails()));
    }

    @Test
    public void getCurrentMemberDetailsNoMemberTest()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getPrincipal()).thenReturn(null);

        MemberService memberService = new MemberService(_mockMemberRepository, _mockRoleRepository, _mockPasswordEncoder);
        MemberDetails actual = memberService.getCurrentMemberDetails();

        assertNull(actual);
    }

    /************************ GET CURRENT MEMBER TESTS *********************************/

    @Test
    public void getCurrentMemberSuccessTest()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getPrincipal()).thenReturn(getMockUser());
        when(_mockMemberRepository.findByEmail(any(String.class))).thenReturn(getMockMember());

        MemberService memberService = new MemberService(_mockMemberRepository, _mockRoleRepository, _mockPasswordEncoder);
        Member actual = memberService.getCurrentMember();

        assertThat(actual, samePropertyValuesAs(getMockMember()));
    }

    @Test
    public void getCurrentMemberNoMemberTest()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getPrincipal()).thenReturn(null);

        MemberService memberService = new MemberService(_mockMemberRepository, _mockRoleRepository, _mockPasswordEncoder);
        Member actual = memberService.getCurrentMember();

        assertNull(actual);
    }

    /************************ HELPER METHODS *********************************/

    private Role getMockRole()
    {
        Set<Member> members = new HashSet<>();
        members.add(getMockMember());

        return new Role(
                0L,
                "",
                members
        );
    }

    private Member getMockMember()
    {
        Member member = new Member();
        member.setEmail("test");
        member.setName("test");
        member.setPhoneNumber("test");
        member.setMemberId(0);

        return member;
    }

    private MemberDetails getMockMemberDetails()
    {
        return new MemberDetails(
                "test",
                "test",
                "test",
                0
        );
    }

    private User getMockUser()
    {
        return new User(
                "test",
                "test",
                new ArrayList<>()
        );
    }

    private List<Role> getMockRoles()
    {
        List<Role> list = new ArrayList<>();
        list.add(getMockRole());

        return list;
    }
}
