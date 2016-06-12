package pl.edu.agh.services;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public interface UserDetails extends Serializable{

    Collection<? extends GrantedAuthority> getAuthorities();
    String getPassword();
    String getLogin();
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();
}
