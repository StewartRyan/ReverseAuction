package Services;

import com.ryan.assignment2.services.Impl.SecurityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SecurityServiceTest {
    @Mock
    AuthenticationManager _mockAuthenticationManager;

    @Mock
    UserDetailsService _mockUserDetailsService;

    @Test
    public void findLoggedInUsernameSuccessTest()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getName()).thenReturn("test");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        SecurityService securityService = new SecurityService(_mockAuthenticationManager, _mockUserDetailsService);
        String actual = securityService.findLoggedInUsername();

        assertEquals("test", actual);
    }

    @Test
    public void findLoggedInUsernameIsAnonymousTest()
    {
        SecurityContext securityContext = mock(SecurityContext.class);
        AnonymousAuthenticationToken anonymousAuthenticationToken = mock(AnonymousAuthenticationToken.class);

        when(securityContext.getAuthentication()).thenReturn(anonymousAuthenticationToken);

        SecurityContextHolder.setContext(securityContext);

        SecurityService securityService = new SecurityService(_mockAuthenticationManager, _mockUserDetailsService);
        String actual = securityService.findLoggedInUsername();

        assertEquals("", actual);
    }

    @Test
    public void autoLoginSuccessTest()
    {
        User mockUser = mock(User.class);
        UsernamePasswordAuthenticationToken mockUsernamePasswordAuthToken = mock(UsernamePasswordAuthenticationToken.class);

        when(mockUsernamePasswordAuthToken.isAuthenticated()).thenReturn(true);
        when(mockUser.getAuthorities()).thenReturn(getMockGrantedAuthorities());
        when(_mockUserDetailsService.loadUserByUsername(any(String.class))).thenReturn(mockUser);

        SecurityService securityService = new SecurityService(_mockAuthenticationManager, _mockUserDetailsService);
        securityService.autoLogin("test", "test");
    }

    /************************  HELPER METHODS   ************************/

    private Set<GrantedAuthority> getMockGrantedAuthorities()
    {
        Set<GrantedAuthority> authorities = new HashSet<>();
        GrantedAuthority grantedAuthority = (GrantedAuthority) () -> "test";

        authorities.add(grantedAuthority);

        return authorities;
    }
}
