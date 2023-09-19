package by.teachmeskills.webservice.controllers;


import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.ProductService;
import by.teachmeskills.webservice.services.impl.ProductServiceImpl;
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
@RequestMapping("/product")
@Valid
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable @Min(0) int id) {
        return Optional.ofNullable(productService.findById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.read(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public void createProduct(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            productService.create(productDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @PutMapping("/update")
    public void updateProduct(@RequestBody @Valid ProductDto productDto, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            productService.update(productDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable @Min(0) Integer id) {
        productService.delete(id);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable @Min(0) int id) {
        return new ResponseEntity<>(productService.getProductsByCategory(id), HttpStatus.OK);
    }


}
