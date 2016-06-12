package pl.edu.agh.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import pl.edu.agh.model.Role;

public final class User {
    @Id
    private String id;
    private String login;
    private String password;
    @JsonIgnore
    private Role role;

    public User() {}

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonCreator
    public User(@JsonProperty("id") String id, @JsonProperty("login") String login,
                    @JsonProperty("password") String password,
                    @JsonProperty("role") Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        setPassword(password);
    }

    @Override
    @JsonIgnore
    public String toString() {
        return "{"+id+", "+login+", "+password+", "+role.toString()+"}";
    }
}