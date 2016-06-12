package pl.edu.agh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.auth.Credentials;
import pl.edu.agh.model.user.User;

import java.util.Optional;

/**
 * Created by Roksana on 2016-05-31.
 */
@Service("authenticationServiceImpl")
public class AuthenticationServiceImpl implements AuthenticationService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService service;


    @Override
    public void authenticateUser(Credentials credentials) throws AuthenticationException {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void removeCurrentAuthenticationToken() {
        SecurityContextHolder.clearContext();

    }

    @Override
    public Optional<User> getCurrentUser() {
        final String username = getUsernameFromContext();
        return service.findByLogin(username);
    }

    private String getUsernameFromContext() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();
        return authentication.getName();
    }
}
