package by.teachmeskills.webservice.repositories.impl;


import by.teachmeskills.webservice.entities.Order;
import by.teachmeskills.webservice.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void create(Order order) throws DBConnectionException {
        entityManager.persist(order);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        Order order = entityManager.find(Order.class, id);
        entityManager.remove(order);
    }

    @Override
    public List<Order> read() throws DBConnectionException {
        return entityManager.createQuery("select o from Order o").getResultList();
    }

    @Override
    public Order findById(int id) throws DBConnectionException {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> findByUserId(int id) throws DBConnectionException {
        return entityManager.createQuery("select o from Order o where o.user.id=:id").setParameter("id", id).getResultList();
    }
}
