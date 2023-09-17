package by.teachmeskills.webservice.repositories.impl;


import by.teachmeskills.webservice.entities.Category;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createOrUpdate(Category category) {
        try {
            entityManager.merge(category);
        } catch (PersistenceException ex) {
            log.warn("Exception while creating or updating category" + ex.getMessage());
            throw new EntityNotFoundException("Error while creating or updating category");
        }
    }

    @Override
    public void delete(int id) {
        try {
            Category category = entityManager.find(Category.class, id);
            entityManager.remove(category);
        } catch (PersistenceException ex) {
            log.warn("Exception while deleting category" + ex.getMessage());
            throw new EntityNotFoundException(String.format("No category with id %d found", id));
        }
    }

    @Override
    public List<Category> read() {
        try {
            return entityManager.createQuery("select c from Category c ").getResultList();
        } catch (PersistenceException ex) {
            log.warn("Exception while getting all categories" + ex.getMessage());
            throw new EntityNotFoundException("Error while getting all categories");
        }
    }

    @Override
    public Category findById(int id) {
        try {
            return entityManager.find(Category.class, id);
        } catch (PersistenceException ex) {
            log.warn("Exception while finding category by id " + ex.getMessage());
            throw new EntityNotFoundException("Error error,come back later");
        }
    }
}
