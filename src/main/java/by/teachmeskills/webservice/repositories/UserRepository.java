package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.User;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface UserRepository {
    void createOrUpdate(User user);

    void delete(int id);

    public List<User> read();

    User findByEmail(String email);

    User findById(int id);

    User findByEmailAndPassword(String email, String password) throws EntityNotFoundException;

    void updatePassword(String password, String email);

    void updateEmail(String previousEmail, String newEmail);
}
