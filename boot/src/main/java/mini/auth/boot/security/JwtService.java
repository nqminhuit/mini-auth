package mini.auth.boot.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String extractUsername(String jwt);

    String extractUserRole(String jwt);

}
