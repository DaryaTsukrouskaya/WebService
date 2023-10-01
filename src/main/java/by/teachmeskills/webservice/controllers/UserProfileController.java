package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.services.UserService;
import by.teachmeskills.webservice.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "profile", description = "Profile endpoints")
public class UserProfileController {
    private final UserService userService;

    public UserProfileController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Find user information",
            description = "Find certain user information in eshop by id",
            tags = {"profile"})
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
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserData(@PathVariable @Min(0) int id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }
}
