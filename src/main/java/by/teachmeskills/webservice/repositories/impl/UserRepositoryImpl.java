package by.teachmeskills.webservice.repositories.impl;


import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void create(User user) throws DBConnectionException, UserAlreadyExistsException {
        entityManager.persist(user);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public List<User> read() throws DBConnectionException {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    @Override
    public User findById(int id) throws DBConnectionException {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws DBConnectionException {
        Query query = entityManager.createQuery("select u from User u where u.email =: email and u.password =: password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        return (User) query.getSingleResult();
    }

    @Override
    public void updatePassword(String password, String email) throws DBConnectionException {
        Query query = entityManager.createQuery("update User u set u.password=:password where u.email=: email");
        query.setParameter("password", password);
        query.setParameter("email", email);
        query.executeUpdate();

    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void updateEmail(String previousEmail, String newEmail) throws DBConnectionException {
        Query query = entityManager.createQuery("update User u set u.email=:newEmail where u.email=: previousEmail");
        query.setParameter("newEmail", newEmail);
        query.setParameter("previousEmail", previousEmail);
        query.executeUpdate();
    }
}