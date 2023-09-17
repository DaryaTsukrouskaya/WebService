package by.teachmeskills.webservice.controllers;


import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.services.UserService;
import by.teachmeskills.webservice.services.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoryServiceImpl categoryService;
    private final UserService userService;

    @Autowired
    public HomeController(CategoryServiceImpl categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<List<CategoryDto>> getHomePage(@Valid @RequestBody UserDto user) {
        if (userService.authenticate(user.getEmail(), user.getPassword()) != null) {
            return new ResponseEntity<>(categoryService.read(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
