package pl.edu.agh.services;


import pl.edu.agh.forms.UserCreateForm;
import pl.edu.agh.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(UserCreateForm form);
    boolean deleteById(String id);
    List<User> findAll();
    Optional<User> findById(String id);
    Optional<User> findByLogin(String login);
}