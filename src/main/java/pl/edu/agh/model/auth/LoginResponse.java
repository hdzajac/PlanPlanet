package pl.edu.agh.model.auth;

import pl.edu.agh.model.user.User;

/**
 * Created by Roksana on 2016-05-31.
 */
public class LoginResponse {
    private Boolean loggedIn = false;
    private User user = null;

    private LoginResponse(Boolean loggedIn, User user) {
        this.loggedIn = loggedIn;
        this.user = user;
    }

    public static LoginResponse success(User user) {
        return new LoginResponse(true, user);
    }

    public static LoginResponse failed() {
        return new LoginResponse(false, null);
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    private void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }
}

