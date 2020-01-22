package mini.auth.boot.security;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import mini.auth.boot.entities.Customer;
import mini.auth.boot.entities.business.CustomerRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // private static final Logger LOG = LogManager.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        List<Customer> customers = customerRepository.findByUsername(username);
        if (customers.isEmpty()) {
            throw new UsernameNotFoundException("Username not found!");
        }

        if (!password.equals(customers.get(0).getPassword())) {
            throw new BadCredentialsException("Password is invalid!");
        }

        return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
