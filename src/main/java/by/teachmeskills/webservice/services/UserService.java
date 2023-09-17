package by.teachmeskills.webservice.services;


import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface UserService {
    List<UserDto> read();

    void delete(int id);

    void register(UserDto user, String repPassword) throws UserAlreadyExistsException;

    UserDto findById(int id);

    UserDto authenticate(String email, String password);

    void updatePassword(String password, String email);

    void updateEmail(String previousEmail, String newEmail);
    void update(UserDto user);
}
