package by.teachmeskills.webservice.services.impl;


import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.NoOrderAddressException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import by.teachmeskills.springbootproject.repositories.impl.OrderRepositoryImpl;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.OrderService;
import by.teachmeskills.springbootproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public OrderServiceImpl(OrderRepositoryImpl orderRepository, UserServiceImpl userService, CategoryServiceImpl categoryService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Order> read() throws DBConnectionException {
        return orderRepository.read();
    }

    @Override
    public void create(Order order) throws DBConnectionException, UserAlreadyExistsException {
        orderRepository.create(order);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        orderRepository.delete(id);
    }

    @Override
    public Order findById(int id) throws DBConnectionException {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUserId(int id) throws DBConnectionException {
        return orderRepository.findByUserId(id);
    }

    @Override
    public ModelAndView createUserOrder(User user, Cart cart, String address) throws DBConnectionException, UserAlreadyExistsException, NoOrderAddressException {
        if (address.isBlank()) {
            throw new NoOrderAddressException("Введите адрес для заказа!");
        }
        Order order = new Order(cart.getTotalPrice(), LocalDateTime.now(), user, address, cart.getProducts());
        orderRepository.create(order);
        if (user.getOrders() == null || user.getOrders().isEmpty()) {
            List<Order> orders = new ArrayList<>();
            user.setOrders(orders);
        }
        user.getOrders().add(order);
        userService.update(user);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("categories", categoryService.read());
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath());
    }
}
