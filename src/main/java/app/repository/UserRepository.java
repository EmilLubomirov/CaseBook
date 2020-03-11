package app.repository;

import app.domain.entities.User;

import java.util.List;
import java.util.Set;

public interface UserRepository
{
    void registerUser(User user);

    User findByUAndP(String username, String password);

    List<User> getAllUsers();

    User findById(String id);

    void update(User user);
}
