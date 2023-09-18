package by.teachmeskills.webservice.controllers;


import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.dto.LoginUserDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.dto.UpdateUserDto;
import by.teachmeskills.webservice.exceptions.IncorrectRepPasswordException;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.CategoryService;
import by.teachmeskills.webservice.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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

    public UserController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> get(@PathVariable @Min(0) int id) {
        return Optional.ofNullable(userService.findById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAll() {
        return new ResponseEntity<>(userService.read(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public void create(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) throws ValidationException, IncorrectRepPasswordException {
        if (!bindingResult.hasErrors()) {
            userService.register(userDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @PutMapping("/update/{id}")
    public void update(@RequestBody @Valid UpdateUserDto updateUserDto, @PathVariable int id, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            userService.update(id, updateUserDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable @Min(0) Integer id) {
        userService.delete(id);
    }

    @PostMapping("/authenticate")
    ResponseEntity<List<CategoryDto>> authenticate(@RequestBody @Valid LoginUserDto userDto, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            userService.authenticate(userDto);
            return new ResponseEntity<>(categoryService.read(), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }
}
