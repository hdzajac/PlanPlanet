package pl.edu.agh.model.user;

import org.springframework.security.core.authority.AuthorityUtils;
import pl.edu.agh.model.Role;

public class CurrentUser extends org.springframework.security.core.userdetails.User{

    private User user;

    public CurrentUser(User user) {
        super(user.getLogin()
                , user.getPassword()
                , AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return user.getId();
    }

    public Role getRole() {
        return user.getRole();
    }
}
