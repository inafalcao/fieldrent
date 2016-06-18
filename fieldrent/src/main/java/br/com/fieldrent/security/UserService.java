package br.com.fieldrent.security;

import br.com.rbt.celpa.model.security.User;
import br.com.rbt.celpa.postgre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by inafalcao on 12/14/15.
 */

@Service("userService")
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        detailsChecker.check(user);
        return user;
    }

    /*public List<GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        if (role.intValue() == 0) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        }
        return authList;
    }

    public User getUserDetail(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }*/

}
