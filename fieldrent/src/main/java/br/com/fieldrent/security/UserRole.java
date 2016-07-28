package br.com.fieldrent.security;


import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.security.UserAuthority;

/**
 * Created by inafalcao on 12/17/15.
 */
public enum UserRole {

    USER, ADMIN;

    public UserAuthority asAuthorityFor(final Client user) {
        final UserAuthority authority = new UserAuthority();
        authority.setAuthority("ROLE_" + toString());
        authority.setUser(user);
        return authority;
    }

    public static UserRole valueOf(final UserAuthority authority) {
        switch (authority.getAuthority()) {
            case "ROLE_USER":
                return USER;
            case "ROLE_ADMIN":
                return ADMIN;
        }
        throw new IllegalArgumentException("No role defined for authority: " + authority.getAuthority());
    }


}
