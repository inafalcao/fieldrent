package br.com.fieldrent.security;

import br.com.fieldrent.model.Client;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by inafalcao on 12/14/15.
 */
@Service
public class JwtTokenHandler {

    public static final int TOKEN_EXPIRATION_TIME = 30; // in minutes

    private String secret = "field@rent87123!secret";

    @Autowired
    private UserService userDetailsService;

    /*@Autowired
    public JwtTokenHandler(@Value("${token.secret}") String secret) {
        this.secret = secret;
    }*/

    public Client parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userDetailsService.loadUserByUsername(username);
    }

    public String parseUsernameFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }

    public String createTokenForUser(Client user) {
        final long ONE_MINUTE_IN_MILLIS = 60000; // millisecs
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Date afterAddingTenMins=new Date(t + (TOKEN_EXPIRATION_TIME * ONE_MINUTE_IN_MILLIS));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS256, secret)
                .setExpiration(afterAddingTenMins)
                .compact();
    }

}
