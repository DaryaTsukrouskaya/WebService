package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.Order;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface OrderRepository {
    void createOrUpdate(Order order);

    Order findById(int id);

    List<Order> findByUserId(int id);

    void delete(int id);

    List<Order> read();
}
