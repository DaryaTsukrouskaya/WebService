package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.CartDto;
import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.exceptions.NoOrderAddressException;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.OrderService;
import by.teachmeskills.webservice.services.ProductService;
import by.teachmeskills.webservice.services.UserService;
import by.teachmeskills.webservice.services.impl.OrderServiceImpl;
import by.teachmeskills.webservice.services.impl.ProductServiceImpl;
import by.teachmeskills.webservice.services.impl.UserServiceImpl;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Validated
@Tag(name = "cart", description = "Cart endpoints")
public class CartController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    public CartController(ProductServiceImpl productService, UserServiceImpl userService, OrderServiceImpl orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Operation(
            summary = "Find all cart products",
            description = "Find all products in user Cart",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cart products were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cart products not fount - server error"
            )
    })
    @GetMapping("/getProducts")
    public ResponseEntity<List<ProductDto>> getAllProducts(@Valid @RequestBody CartDto cart, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(cart.getProducts(), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Add product",
            description = "Add product to cart",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was added"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @PostMapping("/add/{id}")
    public void addProduct(@PathVariable @Min(0) int id, @Valid @RequestBody CartDto cart, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            cart.addProduct(productService.findById(id));
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }

    }

    @Operation(
            summary = "Delete product",
            description = "Delete product from cart",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was deleted from cart"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@Valid @RequestBody CartDto cart, @PathVariable @Min(0) int id, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            cart.removeProduct(id);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Delete all products",
            description = "Delete all products from cart",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products was deleted from cart"
            )
    })
    @DeleteMapping("/clear")
    public void clear(@Valid @RequestBody CartDto cart, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            cart.clear();
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Create order",
            description = "Create new category",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Order was created",
                    content = @Content(schema = @Schema(contentSchema = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order was not created - server error"
            )
    })
    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> buy(@Valid @RequestBody CartDto cart, @Valid @RequestBody UserDto user, @PathVariable String address, BindingResult bindingResult) throws ValidationException, NoOrderAddressException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(orderService.createUserOrder(user, cart, address), HttpStatus.CREATED);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
