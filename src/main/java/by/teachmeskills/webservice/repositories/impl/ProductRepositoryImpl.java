package by.teachmeskills.webservice.repositories.impl;

import by.teachmeskills.webservice.entities.Category;
import by.teachmeskills.webservice.entities.Product;
import by.teachmeskills.webservice.repositories.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Product create(Product product) {
        try {
            entityManager.persist(product);
            return product;
        } catch (PersistenceException ex) {
            log.warn("Exception while creating or updating category" + ex.getMessage());
            throw new EntityNotFoundException("Error while creating or updating category");
        }
    }

    @Override
    public Product update(Product product) {
        try {
            entityManager.merge(product);
            return product;
        } catch (PersistenceException ex) {
            log.warn("Exception while creating or updating category" + ex.getMessage());
            throw new EntityNotFoundException("Error while creating or updating category");
        }
    }

    @Override
    public void delete(int id) {
        try {
            Product product = entityManager.find(Product.class, id);
            entityManager.remove(product);
        } catch (PersistenceException ex) {
            log.warn("Exception while deleting product" + ex.getMessage());
            throw new EntityNotFoundException(String.format("No product with id %d found", id));
        }
    }

    @Override
    public List<Product> read() {
        try {
            return entityManager.createQuery("select p from Product p").getResultList();
        } catch (PersistenceException ex) {
            log.warn("Exception while reading products" + ex.getMessage());
            throw new EntityNotFoundException("Error while reading products");
        }
    }

    @Override
    public Product findById(int id) {
        try {
            return entityManager.find(Product.class, id);
        } catch (PersistenceException ex) {
            log.warn("Exception while deleting product" + ex.getMessage());
            throw new EntityNotFoundException(String.format("No product with id %d found", id));
        }
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        try {
            return entityManager.createQuery("select p from Product p where p.category.id =: categoryId").setParameter("categoryId", categoryId).getResultList();
        } catch (PersistenceException ex) {
            log.warn(String.format("No products with category id %d found", categoryId) + ex.getMessage());
            throw new EntityNotFoundException(String.format("No products with category id %d found", categoryId));
        }
    }

    @Override
    public List<Product> findProductsByKeywords(String words, int pageNumber, int maxResult) {
        try {
            String searchParameter = "%" + words.trim() + "%";
            return entityManager.createQuery(" select p from Product p where p.name like: searchWords or p.description like: searchWords order by p.name asc").
                    setParameter("searchWords", searchParameter).setFirstResult((pageNumber - 1) * maxResult).setMaxResults(maxResult).getResultList();
        } catch (PersistenceException ex) {
            log.warn(("No products with such keywords found") + ex.getMessage());
            throw new EntityNotFoundException("No products found");
        }
    }

    @Override
    public Long findProductsQuantityByKeywords(String words) {
        try {
            String searchParameter = "%" + words.trim() + "%";
            Long totalRecords = (Long) entityManager.createQuery(" select count (*) from Product p where p.name like: searchWords or p.description like: searchWords order by p.name asc").
                    setParameter("searchWords", searchParameter).getSingleResult();
            return totalRecords;
        } catch (PersistenceException ex) {
            log.warn(("No products with such keywords found") + ex.getMessage());
            throw new EntityNotFoundException("No products found");
        }
    }
}
