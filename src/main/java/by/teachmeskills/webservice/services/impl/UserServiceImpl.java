package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.dto.converters.UserConverter;
import by.teachmeskills.webservice.entities.User;
import by.teachmeskills.webservice.exceptions.UserAlreadyExistsException;
import by.teachmeskills.webservice.repositories.UserRepository;
import by.teachmeskills.webservice.repositories.impl.UserRepositoryImpl;
import by.teachmeskills.webservice.services.CategoryService;
import by.teachmeskills.webservice.services.ProductService;
import by.teachmeskills.webservice.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepository, CategoryServiceImpl categoryRepository, ProductServiceImpl productService, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.categoryService = categoryRepository;
        this.productService = productService;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserDto> read() {
        return userRepository.read().stream().map(userConverter::toDto).toList();
    }

    @Override
    public void register(UserDto user, String repPassword) throws UserAlreadyExistsException {
        try {
            if (repPassword.equals(user.getPassword())) {
                if (userRepository.findByEmail(user.getEmail()) != null) {
                    throw new UserAlreadyExistsException("Такой пользователь уже существует");
                }
            }
        } catch (EntityNotFoundException e) {
            userRepository.createOrUpdate(userConverter.fromDto(user));
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
    public UserDto authenticate(String email, String password) {
        User user = null;
        if (email != null && password != null) {
            user = userRepository.findByEmailAndPassword(email, password);
        }
        return userConverter.toDto(user);
    }

    @Override
    public void updatePassword(String password, String email) {
        userRepository.updatePassword(password, email);

    }

    @Override
    public void updateEmail(String previousEmail, String newEmail) {
        userRepository.updateEmail(previousEmail, newEmail);

    }
    @Override
    public void update(UserDto user) {
        userRepository.createOrUpdate(userConverter.fromDto(user));
    }
}
