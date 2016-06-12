package pl.edu.agh.model.auth;

import pl.edu.agh.model.user.User;

/**
 * Created by Roksana on 2016-05-31.
 */
public class CreateUserResponse {
    private Boolean created = false;
    private User user = null;

    private CreateUserResponse(Boolean created, User user) {
        this.created = created;
        this.user = user;
    }

    public static CreateUserResponse success(User user) {
        return new CreateUserResponse(true, user);
    }

    public static CreateUserResponse failed() {
        return new CreateUserResponse(false, null);
    }

    public Boolean getCreated() {
        return created;
    }

    private void setCreated(Boolean created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }
}

