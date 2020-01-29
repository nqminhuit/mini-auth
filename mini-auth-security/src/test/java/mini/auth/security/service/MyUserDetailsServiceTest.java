package mini.auth.security.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import mini.auth.entity.Customer;
import mini.auth.repository.CustomerRepository;

public class MyUserDetailsServiceTest {

    private static final String DEFAULT_USERNAME = "username";

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private MyUserDetailsService userDetailsService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(customerRepository.findByUsername(DEFAULT_USERNAME)).thenReturn(createMockCustomer());
    }

    private Customer createMockCustomer() {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1L);
        mockCustomer.setUsername(DEFAULT_USERNAME);
        mockCustomer.setRole("user");
        mockCustomer.setPassword("mockPassword");
        return mockCustomer;
    }

    @Test
    public void loadUserByUsername() {
        // given:
        String username = DEFAULT_USERNAME;

        // when:
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // then:
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(userDetails.getPassword()).isEqualTo("mockPassword");
        assertThat(userDetails.getAuthorities()).hasSize(1);
        assertThat(userDetails.getAuthorities().iterator().next())
            .isEqualTo(new SimpleGrantedAuthority("user"));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_UsernameNotFound() {
        // given:
        String username = "someRandomUsername";

        // when:
        userDetailsService.loadUserByUsername(username);
    }

}
