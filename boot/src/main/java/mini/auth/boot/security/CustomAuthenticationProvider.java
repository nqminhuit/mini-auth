package mini.auth.boot.security;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import mini.auth.boot.entities.Customer;
import mini.auth.boot.entities.repository.CustomerRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // private static final Logger LOG = LogManager.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Customer customer = customerRepository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("Username not found!");
        }

        if (!password.equals(customer.getPassword())) {
            throw new BadCredentialsException("Password is invalid!");
        }

        return new UsernamePasswordAuthenticationToken(
            username, password, Arrays.asList(new SimpleGrantedAuthority(customer.getRole())));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
