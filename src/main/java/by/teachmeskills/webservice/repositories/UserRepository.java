package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.User;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface UserRepository {
    void create(User user);

    void delete(int id);

    List<User> read();

    User findById(int id);

    User findByEmailAndPassword(String email, String password) throws EntityNotFoundException;

    void update(User user);

}
