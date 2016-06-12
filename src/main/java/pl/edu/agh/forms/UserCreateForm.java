package pl.edu.agh.forms;


import pl.edu.agh.model.Role;

public class UserCreateForm {
    private String firstName = "";
    private String lastName = "";
    private String login = "";

    private String password = "";
    private String password_verify = "";

    private Role role = Role.REGISTEREDUSER;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_verify() {
        return password_verify;
    }

    public void setPassword_verify(String password_verify) {
        this.password_verify = password_verify;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "First: " + firstName + " Last: " + lastName + " Login: " + login + " Password: " + password;
    }
}
