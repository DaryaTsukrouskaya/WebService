package by.teachmeskills.webservice.services;


import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.NoOrderAddressException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface OrderService extends BaseService<Order> {
    Order findById(int id) throws DBConnectionException;

    List<Order> findByUserId(int id) throws DBConnectionException;
    ModelAndView createUserOrder(User user, Cart cart, String address) throws DBConnectionException, UserAlreadyExistsException, NoOrderAddressException;
}
