package by.teachmeskills.webservice.controllers;


import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.CategoryService;
import by.teachmeskills.webservice.services.ProductService;
import by.teachmeskills.webservice.services.impl.CategoryServiceImpl;
import by.teachmeskills.webservice.services.impl.ProductServiceImpl;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@Validated
@Tag(name = "category", description = "Category endpoints")
public class CategoryController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public CategoryController(ProductServiceImpl productService, CategoryServiceImpl categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Find all category products",
            description = "Find all category products",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Products not fount - server error"
            )
    })
    @GetMapping("/products/{id}")
    public ResponseEntity<List<ProductDto>> getCategoryProducts(@PathVariable @Min(0) int id) {
        return new ResponseEntity<>(productService.getProductsByCategory(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Find all categories",
            description = "Find all existed categories in Eshop",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All categories were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Categories not found - server error"
            )
    })
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(categoryService.read(), HttpStatus.OK);
    }

    @Operation(
            summary = "Find certain category",
            description = "Find certain existed category in Eshop by its id",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category was found by its id",
                    content = @Content(schema = @Schema(contentSchema = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category not found - server error"
            )
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable @Min(0) int id) {
        return Optional.ofNullable(categoryService.findById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Create category",
            description = "Create new category",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category was created",
                    content = @Content(schema = @Schema(contentSchema = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category not created - server error"
            )
    })
    @PostMapping("/create")
    public void createCategory(@Valid @RequestBody CategoryDto categoryDto, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            categoryService.create(categoryDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Update category",
            description = "Update existed category",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category was updated",
                    content = @Content(schema = @Schema(contentSchema = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category was not updated - server error"
            )
    })
    @PutMapping("/update")
    public void updateCategory(@RequestBody @Valid CategoryDto categoryDto, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            categoryService.update(categoryDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Delete category",
            description = "Delete existed category from eshop",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category was deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category was not deleted - server error"
            )
    })
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable @Min(0) Integer id) {
        categoryService.delete(id);
    }

    @Operation(
            summary = "Save categories from file",
            description = "Save categories from csv file and persist to database",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories was loaded",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Categories was not loaded - server error"
            )
    })
    @PostMapping("/loadFromFile")
    public ResponseEntity<CategoryDto> loadFromFile(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity(categoryService.saveCategoriesFromFile(file), HttpStatus.OK);
    }

    @Operation(
            summary = "Save categories to file",
            description = "Save categories to .csv file ",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories was saved"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Categories was not loaded - server error"
            )
    })
    @PostMapping("/loadCsvFile")
    public void loadToFile(HttpServletResponse servletResponse) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        categoryService.saveCategoriesToFile(servletResponse);
    }
}

