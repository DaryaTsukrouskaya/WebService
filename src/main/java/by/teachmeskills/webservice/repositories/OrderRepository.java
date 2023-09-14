package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.Order;

import java.util.List;

public interface OrderRepository  {
    Order findById(int id) throws DBConnectionException;

    List<Order> findByUserId(int id) throws DBConnectionException;
}
