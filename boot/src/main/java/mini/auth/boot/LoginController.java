package mini.auth.boot;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mini.auth.boot.security.CustomAuthenticationProvider;

@Api(description = "Handles user login")
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @ApiOperation(value = "user provides credentials")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest req) {
        try {
            authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("username not found!");
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid password");
        }

        String jwt = "you are really " + req.getUsername() + " because you provided valid password: "
            + req.getPassword() + ". Here, JWT for you!";

        return ResponseEntity.ok(jwt);
    }

}
