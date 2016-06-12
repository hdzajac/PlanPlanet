package pl.edu.agh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.forms.UserCreateForm;
import pl.edu.agh.model.Role;
import pl.edu.agh.model.user.User;
import pl.edu.agh.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public final class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
        //repository.save(new User("user", new BCryptPasswordEncoder().encode("user"), Role.ADMIN));
    }

    @Override
    public User create(UserCreateForm form) {
        User u = new User(form.getLogin(),
                            new BCryptPasswordEncoder().encode(form.getPassword()),
                            form.getRole());
        return repository.save(u);
    }

    @Override
    public boolean deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findOne(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }
}