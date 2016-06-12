package pl.edu.agh.model.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Roksana on 2016-05-31.
 */
public class Credentials {
    private String password;
    private String username;

    @JsonCreator
    public Credentials(@JsonProperty("login") String username,
                       @JsonProperty("password") String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

