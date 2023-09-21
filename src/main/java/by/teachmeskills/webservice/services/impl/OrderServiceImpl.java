package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.dto.CartDto;
import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.dto.converters.OrderConverter;
import by.teachmeskills.webservice.dto.converters.ProductConverter;
import by.teachmeskills.webservice.dto.converters.UserConverter;
import by.teachmeskills.webservice.entities.Order;
import by.teachmeskills.webservice.exceptions.NoOrderAddressException;
import by.teachmeskills.webservice.repositories.OrderRepository;
import by.teachmeskills.webservice.repositories.UserRepository;
import by.teachmeskills.webservice.repositories.impl.OrderRepositoryImpl;
import by.teachmeskills.webservice.services.CategoryService;
import by.teachmeskills.webservice.services.OrderService;
import by.teachmeskills.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final OrderConverter orderConverter;
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final ProductConverter productConverter;

    public OrderServiceImpl(OrderRepositoryImpl orderRepository, UserServiceImpl userService, CategoryServiceImpl categoryService, OrderConverter orderConverter, UserConverter userConverter, UserRepository userRepository, ProductConverter productConverter) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.orderConverter = orderConverter;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.productConverter = productConverter;
    }

    @Override
    public List<OrderDto> read() {
        return orderRepository.read().stream().map(orderConverter::toDto).toList();
    }

    @Override
    public void create(OrderDto order) {
        orderRepository.createOrUpdate(orderConverter.fromDto(order));
    }

    @Override
    public void delete(int id) {
        orderRepository.delete(id);
    }

    @Override
    public OrderDto findById(int id) {
        return orderConverter.toDto(orderRepository.findById(id));
    }

    @Override
    public List<OrderDto> findByUserId(int id) {
        return orderRepository.findByUserId(id).stream().map(orderConverter::toDto).toList();
    }

    @Override
    public void update(OrderDto order) {
        orderRepository.createOrUpdate(orderConverter.fromDto(order));
    }

    @Override
    public OrderDto createUserOrder(UserDto user, CartDto cart, String address) throws NoOrderAddressException {
        if (address.isBlank()) {
            throw new NoOrderAddressException("Введите адрес для заказа!");
        }
        Order order = new Order(cart.getTotalPrice(), LocalDateTime.now(), userConverter.fromDto(user), address, cart.getProducts().stream().map(productConverter::fromDto).toList());
        orderRepository.createOrUpdate(order);
        if (user.getOrders() == null || user.getOrders().isEmpty()) {
            List<OrderDto> orders = new ArrayList<>();
            user.setOrders(orders);
        }
        user.getOrders().add(orderConverter.toDto(order));
        userRepository.update(userConverter.fromDto(user));
        cart.clear();
        return orderConverter.toDto(order);
    }
}
