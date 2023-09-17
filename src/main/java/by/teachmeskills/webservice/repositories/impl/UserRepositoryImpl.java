package by.teachmeskills.webservice.repositories.impl;


import by.teachmeskills.webservice.entities.User;
import by.teachmeskills.webservice.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createOrUpdate(User user) {
        try {
            entityManager.merge(user);
        } catch (
                PersistenceException ex) {
            log.warn("Exception while creating or updating user" + ex.getMessage());
            throw new EntityNotFoundException("Error while creating or updating category");
        }
    }

    @Override
    public void delete(int id) {
        try {
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
        } catch (
                PersistenceException ex) {
            log.warn("Exception while deleting user" + ex.getMessage());
            throw new EntityNotFoundException("Error while deleting user");
        }
    }

    @Override
    public List<User> read() {
        try {
            return entityManager.createQuery("select u from User u").getResultList();
        } catch (
                PersistenceException ex) {
            log.warn("Exception while getting all users" + ex.getMessage());
            throw new EntityNotFoundException("Error while getting all users");
        }
    }

    @Override
    public User findById(int id) {
        try {
            return entityManager.find(User.class, id);
        } catch (PersistenceException ex) {
            log.warn(String.format("No user with id %d found", id) + ex.getMessage());
            throw new EntityNotFoundException(String.format("No user with id %d found", id));
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EntityNotFoundException {
        try {
            Query query = entityManager.createQuery("select u from User u where u.email =: email and u.password =: password");
            query.setParameter("email", email);
            query.setParameter("password", password);
            return (User) query.getSingleResult();
        } catch (PersistenceException ex) {
            log.warn(String.format("No user with email %s and password %s found", email, password) + ex.getMessage());
            throw new EntityNotFoundException(String.format("No user with email %s and password %s found", email, password));
        }
    }

    @Override
    public User findByEmail(String email) throws EntityNotFoundException {
        try {
            Query query = entityManager.createQuery("select u from User u where u.email =: email");
            query.setParameter("email", email);
            return (User) query.getSingleResult();
        } catch (PersistenceException ex) {
            log.warn(String.format("No user with email %s found", email) + ex.getMessage());
            throw new EntityNotFoundException(String.format("No user with email %s found", email));
        }
    }

    @Override
    public void updatePassword(String password, String email) {
        try {
            Query query = entityManager.createQuery("update User u set u.password=:password where u.email=: email");
            query.setParameter("password", password);
            query.setParameter("email", email);
            query.executeUpdate();
        } catch (PersistenceException ex) {
            log.warn(String.format("No user with email %s found", email) + ex.getMessage());
            throw new EntityNotFoundException(String.format("No user with email %s found", email));
        }

    }


    @Override
    public void updateEmail(String previousEmail, String newEmail) {
        try {
            Query query = entityManager.createQuery("update User u set u.email=:newEmail where u.email=: previousEmail");
            query.setParameter("newEmail", newEmail);
            query.setParameter("previousEmail", previousEmail);
            query.executeUpdate();
        } catch (PersistenceException ex) {
            log.warn(String.format("No user with email %s found", previousEmail) + ex.getMessage());
            throw new EntityNotFoundException(String.format("No user with email %s found", previousEmail));
        }
    }
}