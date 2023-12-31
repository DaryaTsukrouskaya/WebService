package by.teachmeskills.webservice.services;


import by.teachmeskills.webservice.dto.LoginUserDto;
import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.dto.UpdateUserDto;
import by.teachmeskills.webservice.entities.User;
import by.teachmeskills.webservice.exceptions.IncorrectRepPasswordException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> read();

    void delete(int id);

    void register(UserDto user) throws IncorrectRepPasswordException;

    UserDto findById(int id);

    void update(int id, UpdateUserDto updateUserDto);

    List<OrderDto> getUserOrdersPaged(int id, int pageNumber, int pageSize);

    Optional<User> findByLogin(String login);
}
