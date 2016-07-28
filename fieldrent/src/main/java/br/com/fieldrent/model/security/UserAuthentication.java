package br.com.fieldrent.model.security;

import br.com.fieldrent.model.Client;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by inafalcao on 12/15/15.
 */
public class UserAuthentication implements Authentication {

    private final Client user;
    private boolean authenticated = true;

    public UserAuthentication(Client user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Client getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }
    @Override
    public String getName() {
        return null;
    }
}
