package by.teachmeskills.webservice.repositories.impl;


import by.teachmeskills.webservice.entities.Category;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void create(Category category) throws DBConnectionException {
        entityManager.persist(category);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        Category category = entityManager.find(Category.class, id);
        entityManager.remove(category);
    }

    @Override
    public List<Category> read() throws DBConnectionException {
        return entityManager.createQuery("select c from Category c ").getResultList();
    }

    @Override
    public Category findById(int id) throws DBConnectionException {
        return entityManager.find(Category.class, id);
    }
}
