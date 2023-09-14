package by.teachmeskills.webservice.controllers;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.services.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/profile")
public class UserProfileController {
    private final UserService userService;

    public UserProfileController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping
    public ModelAndView getUserPage(@SessionAttribute("user") User user) throws DBConnectionException {
        return userService.userServicePage(user);
    }
}
