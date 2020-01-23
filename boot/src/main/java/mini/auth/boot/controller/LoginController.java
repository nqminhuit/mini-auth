package mini.auth.boot.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mini.auth.boot.AuthenticationRequest;
import mini.auth.boot.security.CustomAuthenticationProvider;
import mini.auth.boot.security.JwtService;

@Api(description = "Handles user login")
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @ApiOperation(value = "user provides credentials")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest req) {
        String password = req.getPassword();
        String username = req.getUsername();

        Authentication authentication = null;
        try {
            authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("username not found!");
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid password");
        }

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("could not perform authentication for user " + username);
        }

        UserDetails userDetails = new User(username, password, authentication.getAuthorities());

        return ResponseEntity.ok(jwtService.generateToken(userDetails));
    }

}
