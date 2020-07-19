package Services;

import com.ryan.assignment2.domain.entities.Member;
import com.ryan.assignment2.domain.entities.Role;
import com.ryan.assignment2.repositories.IRoleRepository;
import com.ryan.assignment2.services.Impl.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RoleServiceTest
{
    @Mock
    IRoleRepository _mockRoleRepository;

    @Test
    public void insertRoleSuccessTest()
    {
        when(_mockRoleRepository.save(any(Role.class))).thenReturn(getMockRole());

        RoleService roleService = new RoleService(_mockRoleRepository);
        Role actual = roleService.insertRoles();

        assertThat(actual, samePropertyValuesAs(getMockRole()));
    }


    @Test
    public void saveRoleSuccessTest()
    {
        when(_mockRoleRepository.save(any(Role.class))).thenReturn(getMockRole());

        RoleService roleService = new RoleService(_mockRoleRepository);
        Role actual = roleService.save(getMockRole());

        assertThat(actual, samePropertyValuesAs(getMockRole()));
    }


    /************************ HELPER METHODS *********************************/

    private Role getMockRole()
    {
        Set<Member> members = new HashSet<>();
        members.add(new Member());

        return new Role(
                0L,
                "standard",
                members
        );
    }
}
