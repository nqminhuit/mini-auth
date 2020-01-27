package mini.auth.boot.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mini.auth.security.model.AuthenticationRequest;
import mini.auth.security.model.AuthenticationResponse;
import mini.auth.security.service.AuthenticationService;

@Api(description = "Handles user login")
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;


    @ApiOperation(value = "user provides credentials")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest req) {
        String password = req.getPassword();
        String username = req.getUsername();

        AuthenticationResponse authenticate = authenticationService.authenticate(username, password);
        if (StringUtils.isBlank(authenticate.getError())) {
            return ResponseEntity.ok(authenticate);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(authenticate.getError());
    }

}
