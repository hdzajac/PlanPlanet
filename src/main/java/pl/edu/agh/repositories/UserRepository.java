package pl.edu.agh.repositories;

import org.springframework.data.repository.Repository;
import pl.edu.agh.model.user.User;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User, String> {
    Optional<User> findOne(String id);
    List<User> findAll();
    User save(User user);
    Optional<User> findByLogin(String login);
    boolean deleteById(String id);
}