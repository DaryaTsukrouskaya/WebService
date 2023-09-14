package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.User;

public interface UserRepository {
    User findById(int id) throws DBConnectionException;

    User findByEmailAndPassword(String email, String password) throws DBConnectionException;

    void updatePassword(String password, String email) throws DBConnectionException;

    void updateEmail(String previousEmail, String newEmail) throws DBConnectionException;
    void update(User user);
}
