package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.dto.UpdateUserDto;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.CategoryService;
import by.teachmeskills.webservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Tag(name = "user", description = "User endpoints")
public class UserController {
    private final CategoryService categoryService;
    private final UserService userService;

    public UserController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Operation(
            summary = "Find certain user",
            description = "Find certain user of eshop by id",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User was found by id",
                    content = @Content(schema = @Schema(contentSchema = UserDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User not found - server error"
            )
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> get(@PathVariable @Min(0) int id) {
        return Optional.ofNullable(userService.findById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Find all users",
            description = "Find all existed users of eshop",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All users were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Users was not found - server error"
            )
    })
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDto>> getAll() {
        return new ResponseEntity<>(userService.read(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update user",
            description = "Update certain user by id",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category was updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category was not updated - server error"
            )
    })
    @PutMapping("/update/{id}")
    public void update(@RequestBody @Valid UpdateUserDto updateUserDto, @PathVariable int id, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            userService.update(id, updateUserDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Delete user",
            description = "Delete existed user from eshop",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User was deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User was not deleted - server error"
            )
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable @Min(0) Integer id) {
        userService.delete(id);
    }

}
