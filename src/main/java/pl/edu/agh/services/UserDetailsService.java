package pl.edu.agh.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.edu.agh.model.user.CurrentUser;


public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService{
    CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException;
}
