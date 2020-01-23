package mini.auth.boot.security.bean;

import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import mini.auth.boot.security.JwtService;

@Component
public class JwtServiceBean implements JwtService {

    private static final Logger LOG = LogManager.getLogger(JwtServiceBean.class);

    @Override
    public String generateToken(UserDetails userDetails) {
        Date current = new Date();
        Date expire = DateUtils.addMinutes(current, 1);

        // only works if 1 role per user:
        String authority = userDetails.getAuthorities().iterator().next().getAuthority();

        LOG.info("generate JWT with sub = {}, iat = {}, exp = {} and role = {}",
            userDetails.getUsername(), current, expire, authority);

        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(current)
            .setExpiration(expire)
            .claim("role", authority)
            .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
            .compact();
    }

}
