package mini.auth.security.bean;

import java.util.Date;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import mini.auth.security.service.JwtService;

@Component
public class JwtServiceBean implements JwtService {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final Logger LOG = LogManager.getLogger(JwtServiceBean.class);

    @Override
    public String generateToken(UserDetails userDetails) {
        Date current = new Date();
        Date expireIn3Mins = DateUtils.addMinutes(current, 3);

        // only works if 1 role per user:
        String authority = userDetails.getAuthorities().iterator().next().getAuthority();

        LOG.info("generate JWT with sub = {}, iat = {}, exp = {} and role = {}",
            userDetails.getUsername(), current, expireIn3Mins, authority);

        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(current)
            .setExpiration(expireIn3Mins)
            .claim("role", authority)
            .signWith(SECRET_KEY)
            .compact();
    }

    @Override
    public String extractUsername(String jwt) {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(jwt)
            .getBody().getSubject();
    }

    @Override
    public String extractUserRole(String jwt) {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(jwt)
            .getBody().get("role", String.class);
    }

}
