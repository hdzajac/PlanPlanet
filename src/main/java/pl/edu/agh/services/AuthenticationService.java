package pl.edu.agh.services;

import pl.edu.agh.model.auth.Credentials;
import pl.edu.agh.model.user.User;

import java.util.Optional;

/**
 * Created by Roksana on 2016-05-31.
 */
public interface AuthenticationService {
    Optional<User> getCurrentUser();

    void authenticateUser(Credentials credentials);

    void removeCurrentAuthenticationToken();
}
