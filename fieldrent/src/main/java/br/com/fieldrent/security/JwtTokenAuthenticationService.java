package br.com.fieldrent.security;


import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by inafalcao on 12/14/15.
 */

@Service("jwtTokenAuthenticationService")
public class JwtTokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    @Autowired
    private JwtTokenHandler tokenHandler;

    public JwtTokenAuthenticationService() {

    }

    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final Client user = authentication.getDetails();
        response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final Client user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }

}
