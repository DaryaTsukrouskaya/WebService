package by.teachmeskills.webservice.controllers;


import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.exceptions.UserAlreadyExistsException;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.CategoryService;
import by.teachmeskills.webservice.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public UserController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable @Min(0) int id) {
        return Optional.ofNullable(userService.findById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.read(), HttpStatus.OK);
    }

    @PostMapping("/create/{repPassword}")
    public void createUser(@Valid @RequestBody UserDto userDto, @PathVariable String repPassword, BindingResult bindingResult) throws ValidationException, UserAlreadyExistsException {
        if (!bindingResult.hasErrors()) {
            userService.register(userDto, repPassword);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            userService.update(userDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable @Min(0) Integer id) {
        userService.delete(id);
    }

    @PutMapping("/updatePassword/{email}/{password}")
    public void updatePassword(@PathVariable String password, @PathVariable String email) {
        userService.updatePassword(password, email);

    }

    @PutMapping("/updateEmail/{newEmail}/{previousEmail}")
    public void updateEmail(String previousEmail, String newEmail) {
        userService.updateEmail(previousEmail, newEmail);
    }

    @GetMapping("/authenticate/{email}/{password}")
    ResponseEntity<List<CategoryDto>> authenticate(@PathVariable String email, @PathVariable String password) {
        if (userService.authenticate(email, password) != null) {
            return new ResponseEntity<>(categoryService.read(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
