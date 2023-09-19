package by.teachmeskills.webservice.controllers;


import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.CategoryService;
import by.teachmeskills.webservice.services.ProductService;
import by.teachmeskills.webservice.services.impl.CategoryServiceImpl;
import by.teachmeskills.webservice.services.impl.ProductServiceImpl;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public CategoryController(ProductServiceImpl productService, CategoryServiceImpl categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<List<ProductDto>> getCategoryProducts(@PathVariable @Min(0) int id) {
        return new ResponseEntity<>(productService.getProductsByCategory(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(categoryService.read(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable @Min(0) int id) {
        return Optional.ofNullable(categoryService.findById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public void createCategory(@Valid @RequestBody CategoryDto categoryDto, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            categoryService.create(categoryDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @PutMapping("/update")
    public void updateCategory(@RequestBody @Valid CategoryDto categoryDto, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            categoryService.update(categoryDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable @Min(0) Integer id) {
        categoryService.delete(id);
    }
}

