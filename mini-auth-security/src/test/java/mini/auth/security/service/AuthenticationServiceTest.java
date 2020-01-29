package mini.auth.security.service;

import static mini.auth.security.utils.UserTestUtils.DEFAULT_JWT_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import mini.auth.security.CustomAuthenticationProvider;
import mini.auth.security.bean.JwtServiceBean;
import mini.auth.security.model.AuthenticationResponse;

public class AuthenticationServiceTest {

    @Mock
    private CustomAuthenticationProvider authenticationProviderMock;

    @Mock
    private JwtServiceBean jwtServiceBeanMock;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(authenticationProviderMock.authenticate(mockUpat()))
            .thenReturn(new UsernamePasswordAuthenticationToken("mockUsername", "mockPassword"));

        when(jwtServiceBeanMock.generateToken(Mockito.any(UserDetails.class)))
            .thenReturn(DEFAULT_JWT_TOKEN);
    }

    private UsernamePasswordAuthenticationToken mockUpat() {
        return new UsernamePasswordAuthenticationToken("mockUsername", "mockPassword");
    }

    @Test
    public void authenticate_Success() {
        // given:
        String username = "mockUsername";
        String password = "mockPassword";

        // when:
        AuthenticationResponse res = authenticationService.authenticate(username, password);

        // then:
        assertThat(res).isNotNull();
        assertThat(res.getError()).isBlank();
        assertThat(res.getJwt()).isEqualTo(DEFAULT_JWT_TOKEN);
    }
}
