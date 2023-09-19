package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.services.UserService;
import by.teachmeskills.webservice.services.impl.UserServiceImpl;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/profile")
@Validated
public class UserProfileController {
    private final UserService userService;

    public UserProfileController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserData(@PathVariable @Min(0) int id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }
}
