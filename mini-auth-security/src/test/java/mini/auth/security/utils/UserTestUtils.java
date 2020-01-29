package mini.auth.security.utils;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserTestUtils {

    public static final Collection<? extends GrantedAuthority> ADMIN_AUTHORITY =
        Arrays.asList(new SimpleGrantedAuthority("admin"));

    public static final Collection<? extends GrantedAuthority> USER_AUTHORITY =
        Arrays.asList(new SimpleGrantedAuthority("user"));

    public static final String DEFAULT_JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9."
        + "eyJzdWIiOiJiYXRtYW4iLCJpYXQiOjE1ODAyOTUyMzUsImV4cCI6MTU4MDI5NTQxNSwicm9sZSI6ImFkbWluIn0."
        + "Hs1vkvtFSPdS2P9dbSQg38vh5w5BSWZYp1sfvqS33q0";

    public static UserDetails mockUserDetails(
        String username, String password, Collection<? extends GrantedAuthority> authorities) {

        return new User(username, password, authorities);
    }

}
