package mini.auth.boot.security.filter;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import mini.auth.boot.security.JwtService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger LOG = LogManager.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(jwt)) {
            String username = jwtService.extractUsername(jwt);
            String userRole = jwtService.extractUserRole(jwt);

            UserDetails userDetails = new User(username, "somepassword",
                Arrays.asList(new SimpleGrantedAuthority(userRole)));

            UsernamePasswordAuthenticationToken upat =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(upat);
        }

        filterChain.doFilter(request, response);
    }


}
