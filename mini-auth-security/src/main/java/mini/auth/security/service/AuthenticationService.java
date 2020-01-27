package mini.auth.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import mini.auth.security.CustomAuthenticationProvider;
import mini.auth.security.model.AuthenticationResponse;

@Component
public class AuthenticationService {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    // private static final Logger LOG = LogManager.getLogger(AuthenticationService.class);

    public AuthenticationResponse authenticate(String username, String password) {
        Authentication authentication = null;
        try {
            authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (UsernameNotFoundException e) {
            return new AuthenticationResponse(null, "username not found!");
        }
        catch (BadCredentialsException e) {
            return new AuthenticationResponse(null, "invalid password");
        }

        if (authentication == null) {
            return new AuthenticationResponse(null, "could not perform authentication for user " + username);
        }

        UserDetails userDetails = new User(username, password, authentication.getAuthorities());

        String token = jwtService.generateToken(userDetails);

        return new AuthenticationResponse(token);
    }
}
