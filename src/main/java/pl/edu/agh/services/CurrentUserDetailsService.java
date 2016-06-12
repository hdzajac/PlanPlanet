package pl.edu.agh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.user.CurrentUser;
import pl.edu.agh.model.user.User;

@Service
public class CurrentUserDetailsService implements UserDetailsService{

    private final UserService service;

    @Autowired
    public CurrentUserDetailsService(@Qualifier("userServiceImpl") UserService service){
        this.service = service;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username));
        System.out.println("LOAD"+user.toString());
        CurrentUser currentUser = new CurrentUser(user);
        System.out.println("CURR"+currentUser.getId()+"  "+currentUser.getPassword());
        return currentUser;
    }


}
