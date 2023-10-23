package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.dto.LoginUserDto;
import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.dto.converters.OrderConverter;
import by.teachmeskills.webservice.dto.UpdateUserDto;
import by.teachmeskills.webservice.dto.converters.UserConverter;
import by.teachmeskills.webservice.entities.Role;
import by.teachmeskills.webservice.entities.User;
import by.teachmeskills.webservice.exceptions.IncorrectRepPasswordException;
import by.teachmeskills.webservice.repositories.OrderRepository;
import by.teachmeskills.webservice.repositories.UserRepository;
import by.teachmeskills.webservice.services.CategoryService;
import by.teachmeskills.webservice.services.ProductService;
import by.teachmeskills.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderConverter orderConverter;
    private final UserConverter userConverter;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, CategoryServiceImpl categoryRepository, ProductServiceImpl productService, OrderConverter orderConverter, UserConverter userConverter, OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryService = categoryRepository;
        this.productService = productService;
        this.orderConverter = orderConverter;
        this.userConverter = userConverter;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> read() {
        return userRepository.findAll().stream().map(userConverter::toDto).toList();
    }

    @Override
    public void register(UserDto userDto) throws IncorrectRepPasswordException {
        if (userDto.getRepPassword().equals(userDto.getPassword())) {
            User user = new User();
            user.setName(userDto.getName());
            user.setSurname(userDto.getSurname());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setBirthDate(userDto.getBirthDate());
            user.setOrders(userDto.getOrders().stream().map(orderConverter::fromDto).toList());
            user.setRoles(List.of(Role.builder().id(2).name("USER").build()));
            userRepository.save(user);
        } else {
            throw new IncorrectRepPasswordException("пароли не совпадают");
        }
    }


    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findById(int id) {
        return userConverter.toDto(userRepository.findById(id));
    }

    @Override
    public UserDto authenticate(LoginUserDto user) {
        return userConverter.toDto(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()));
    }

    @Override
    public void update(int id, UpdateUserDto userDto) {
        User user = userRepository.findById(id);
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<OrderDto> getUserOrdersPaged(int id, int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        User user = userRepository.findById(id);
        if (!user.getOrders().isEmpty()) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("orderDate").descending());
            List<OrderDto> orders = orderRepository.findByUserId(id, pageable).getContent().stream().map(orderConverter::toDto).toList();
            return orders;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

}
