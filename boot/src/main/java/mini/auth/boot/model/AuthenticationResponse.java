package mini.auth.boot.model;

import java.io.Serializable;

/**
 * this class is to fix "SyntaxError: Unexpected token e in JSON at position 0 at JSON.parse (<anonymous>)
 * at XMLHttpRequest.onLoad (http://localhost:4200/vendor.js:10132:51)
 */
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String jwt;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return this.jwt;
    }

}
