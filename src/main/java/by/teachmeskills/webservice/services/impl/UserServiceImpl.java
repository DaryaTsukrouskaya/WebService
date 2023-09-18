package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.dto.LoginUserDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.dto.converters.OrderConverter;
import by.teachmeskills.webservice.dto.UpdateUserDto;
import by.teachmeskills.webservice.dto.converters.UserConverter;
import by.teachmeskills.webservice.entities.User;
import by.teachmeskills.webservice.exceptions.IncorrectRepPasswordException;
import by.teachmeskills.webservice.repositories.UserRepository;
import by.teachmeskills.webservice.repositories.impl.UserRepositoryImpl;
import by.teachmeskills.webservice.services.CategoryService;
import by.teachmeskills.webservice.services.ProductService;
import by.teachmeskills.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderConverter orderConverter;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepository, CategoryServiceImpl categoryRepository, ProductServiceImpl productService, OrderConverter orderConverter, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.categoryService = categoryRepository;
        this.productService = productService;
        this.orderConverter = orderConverter;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserDto> read() {
        return userRepository.read().stream().map(userConverter::toDto).toList();
    }

    @Override
    public void register(UserDto userDto) throws IncorrectRepPasswordException {
        if (userDto.getRepPassword().equals(userDto.getPassword())) {
            User user = new User();
            user.setName(userDto.getName());
            user.setSurname(userDto.getSurname());
            user.setEmail(userDto.getEmail());
            user.setBirthDate(userDto.getBirthDate());
            user.setOrders(userDto.getOrders().stream().map(orderConverter::fromDto).toList());
            userRepository.create(user);
        } else {
            throw new IncorrectRepPasswordException("пароли не совпадают");
        }
    }


    @Override
    public void delete(int id) {
        userRepository.delete(id);

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
        user.setPassword(userDto.getPassword());
        userRepository.update(user);
    }
}
