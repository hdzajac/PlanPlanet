package pl.edu.agh.model.auth;

import pl.edu.agh.model.user.User;

/**
 * Created by Roksana on 2016-05-31.
 */
public class CreateUserResponse {
    private Boolean created = false;
    private User user = null;
    private String message;

    private CreateUserResponse(Boolean created, User user) {
        this.created = created;
        this.user = user;
    }

    private CreateUserResponse(boolean created, User user, String message) {
        this.created = created;
        this.user = user;
        this.message = message;
    }

    public static CreateUserResponse success(User user) {
        return new CreateUserResponse(true, user);
    }

    public static CreateUserResponse failed(String message) {
        return new CreateUserResponse(false, null, message);
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

