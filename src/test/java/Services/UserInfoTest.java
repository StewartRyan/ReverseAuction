package Services;

import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.domain.entities.Role;
import com.ryan.assignment2.repositories.IMemberRepository;
import com.ryan.assignment2.services.Impl.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserInfoTest
{
    @Mock
    IMemberRepository _mockMemberRepository;

    @Test
    public void loadUserByUsernameSuccessTest()
    {
        when(_mockMemberRepository.findByEmail(any(String.class))).thenReturn(getMockMember());

        UserInfo userInfo = new UserInfo(_mockMemberRepository);
        UserDetails actual = userInfo.loadUserByUsername("test");

        assertThat(actual, samePropertyValuesAs(getMockUser()));
    }

    @Test
    public void loadUserByUsernameExceptionTest()
    {
        when(_mockMemberRepository.findByEmail(any(String.class))).thenReturn(null);

        UserInfo userInfo = new UserInfo(_mockMemberRepository);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userInfo.loadUserByUsername("test");
        });

        String actual = exception.getMessage();

        assertEquals(actual, "test");
    }


    /************************ HELPER METHODS *********************************/

    private Member getMockMember()
    {
        Member member = new Member();
        member.setEmail("test");
        member.setName("test");
        member.setPhoneNumber("test");
        member.setMemberId(0);
        member.setPassHash("test");
        member.setRoles(getMockRoles());

        return member;
    }

    private Set<GrantedAuthority> getMockAuthorities()
    {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("test");

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(grantedAuthority);

        return authorities;
    }

    private User getMockUser()
    {
        return new User(
                "test",
                "test",
                getMockAuthorities()
        );
    }

    private Role getMockRole()
    {
        Set<Member> members = new HashSet<>();
        members.add(new Member());

        return new Role(
                0L,
                "test",
                members
        );
    }

    private Set<Role> getMockRoles()
    {
        Set<Role> roles = new HashSet<>();
        roles.add(getMockRole());

        return roles;
    }
}
