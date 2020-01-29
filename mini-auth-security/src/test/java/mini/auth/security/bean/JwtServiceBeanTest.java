package mini.auth.security.bean;

import static mini.auth.security.utils.UserTestUtils.ADMIN_AUTHORITY;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtServiceBeanTest {

    private JwtServiceBean jwtService;

    @Before
    public void setup() {
        jwtService = new JwtServiceBean();
    }

    @Test
    public void generateToken() {
        // given:
        UserDetails userDetails = new User("batman", "batpass", ADMIN_AUTHORITY);

        // when:
        String jwtToken = jwtService.generateToken(userDetails);

        // then:
        assertThat(jwtToken).isNotNull();
        assertThat(jwtToken).hasSize(152);
        String[] tokenParts = jwtToken.split("\\.");
        assertThat(tokenParts).hasSize(3);
        assertThat(tokenParts[0]).hasSize(20);
        assertThat(tokenParts[1]).hasSize(87);
        assertThat(tokenParts[2]).hasSize(43);
    }

}
