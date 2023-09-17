package by.teachmeskills.webservice.repositories.impl;


import by.teachmeskills.webservice.entities.Order;
import by.teachmeskills.webservice.repositories.OrderRepository;
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
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createOrUpdate(Order order) {
        try {
            entityManager.merge(order);
        } catch (
                PersistenceException ex) {
            log.warn("Exception while creating or updating order" + ex.getMessage());
            throw new EntityNotFoundException("Error while creating or updating order");
        }
    }

    @Override
    public void delete(int id) {
        try {
            Order order = entityManager.find(Order.class, id);
            entityManager.remove(order);
        } catch (PersistenceException ex) {
            log.warn("Exception while deleting order" + ex.getMessage());
            throw new EntityNotFoundException(String.format("No order with id %d found", id));
        }
    }

    @Override
    public List<Order> read() {
        try {
            return entityManager.createQuery("select o from Order o").getResultList();
        } catch (
                PersistenceException ex) {
            log.warn("Exception while getting all orders" + ex.getMessage());
            throw new EntityNotFoundException("Error while getting all orders");
        }
    }

    @Override
    public Order findById(int id) {
        try {
            return entityManager.find(Order.class, id);
        } catch (
                PersistenceException ex) {
            log.warn("Exception while finding order by id" + ex.getMessage());
            throw new EntityNotFoundException(String.format("No order with id %d found", id));
        }
    }

    @Override
    public List<Order> findByUserId(int id) {
        try {
            return entityManager.createQuery("select o from Order o where o.user.id=:id").setParameter("id", id).getResultList();
        } catch (PersistenceException ex) {
            log.warn("Exception while finding orders by user id" + ex.getMessage());
            throw new EntityNotFoundException(String.format("No orders with user id %d found", id));
        }
    }
}
