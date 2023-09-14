package by.teachmeskills.webservice.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    ModelAndView registerUser(User user, String repPassword) throws DBConnectionException;

    User findById(int id) throws DBConnectionException;

    ModelAndView authenticate(String email, String password) throws DBConnectionException;

    void updatePassword(String password, String email) throws DBConnectionException;

    void updateEmail(String previousEmail, String newEmail) throws DBConnectionException;

    ModelAndView userServicePage(User user) throws DBConnectionException;
    ModelAndView checkout(Cart cart);
    void update(User user);

}
