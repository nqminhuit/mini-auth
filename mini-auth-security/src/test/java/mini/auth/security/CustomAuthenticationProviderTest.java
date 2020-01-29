package mini.auth.security;

import static mini.auth.security.utils.UserTestUtils.USER_AUTHORITY;
import static mini.auth.security.utils.UserTestUtils.mockUserDetails;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import mini.auth.security.service.MyUserDetailsService;

public class CustomAuthenticationProviderTest {

    @Mock
    private MyUserDetailsService userDetailsServiceMock;

    @InjectMocks
    private CustomAuthenticationProvider authenticationProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(userDetailsServiceMock.loadUserByUsername("mockUsername"))
            .thenReturn(mockUserDetails("mockUsername", "mockPassword", USER_AUTHORITY));

        when(userDetailsServiceMock.loadUserByUsername("someRandomUsername"))
            .thenThrow(new UsernameNotFoundException("Username not found!"));
    }

    @Test
    public void providerAuthenticate_Success() {
        // given:
        UsernamePasswordAuthenticationToken upat =
            new UsernamePasswordAuthenticationToken("mockUsername", "mockPassword");

        // when:
        Authentication authenticated = authenticationProvider.authenticate(upat);

        // then:
        assertThat(authenticated.isAuthenticated()).isTrue();
    }

    @Test(expected = UsernameNotFoundException.class)
    public void providerAuthenticate_UsernameNotFound() {
        // given:
        UsernamePasswordAuthenticationToken upat =
            new UsernamePasswordAuthenticationToken("someRandomUsername", "mockPassword");

        // when:
        authenticationProvider.authenticate(upat);
    }


    @Test(expected = BadCredentialsException.class)
    public void providerAuthenticate_WrongPassword() {
        // given:
        UsernamePasswordAuthenticationToken upat =
            new UsernamePasswordAuthenticationToken("mockUsername", "wrongPassword");

        // when:
        authenticationProvider.authenticate(upat);
    }

}
