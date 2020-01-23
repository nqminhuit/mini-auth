package mini.auth.boot.security.filter;

import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import mini.auth.boot.security.JwtService;
import mini.auth.boot.security.MyUserDetailsService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";

    private static final String BEARER = "Bearer "; // with a space

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (isNotBlank(authorizationHeader) && contains(authorizationHeader, BEARER)) {

            String jwt = authorizationHeader.substring(BEARER.length());
            if (isNotBlank(jwt)) {
                String username = jwtService.extractUsername(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken upat =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }

        filterChain.doFilter(request, response);
    }

}
