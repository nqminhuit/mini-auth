package mini.auth.security.model;

import java.io.Serializable;

/**
 * this class is to fix "SyntaxError: Unexpected token e in JSON at position 0 at JSON.parse (<anonymous>)
 * at XMLHttpRequest.onLoad (http://localhost:4200/vendor.js:10132:51)
 */
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String jwt;

    private String error;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public AuthenticationResponse(String jwt, String error) {
        this.jwt = null;
        this.error = error;
    }

    public String getJwt() {
        return this.jwt;
    }

    public String getError() {
        return this.error;
    }

}
