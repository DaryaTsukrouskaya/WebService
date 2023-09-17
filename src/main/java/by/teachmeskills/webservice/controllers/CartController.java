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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CartController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public CartController(ProductServiceImpl productService, UserServiceImpl userService, OrderServiceImpl orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/getProducts")
    public ResponseEntity<List<ProductDto>> getAlLProducts(@Valid @RequestBody CartDto cart, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(cart.getProducts(), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @PostMapping("/add/{id}")
    public void addProduct(@PathVariable @Min(0) int id, @Valid @RequestBody CartDto cart, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            cart.addProduct(productService.findById(id));
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductFromCart(@Valid @RequestBody CartDto cart, @PathVariable @Min(0) int id, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            cart.removeProduct(id);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @DeleteMapping("/clear")
    public void clearCart(@Valid @RequestBody CartDto cart, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            cart.clear();
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> buy(@Valid @RequestBody CartDto cart, @Valid @RequestBody UserDto user, @PathVariable String address, BindingResult bindingResult) throws ValidationException, NoOrderAddressException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(orderService.createUserOrder(user, cart, address), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
