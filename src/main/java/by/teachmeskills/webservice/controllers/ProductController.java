package by.teachmeskills.webservice.controllers;


import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.ProductService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@Valid
@Tag(name = "product", description = "Product endpoints")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Find certain product",
            description = "Find certain existed product in Eshop by its id",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was found by its id",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product not found - server error"
            )
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable @Min(0) int id) {
        return Optional.ofNullable(productService.findById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Find all products",
            description = "Find all products",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Products not found - server error"
            )
    })
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.read(), HttpStatus.OK);
    }

    @Operation(
            summary = "Create product",
            description = "Create new product",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product was created",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product was not created - server error"
            )
    })
    @PostMapping("/create")
    public void createProduct(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            productService.create(productDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Update product",
            description = "Update existed product",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was updated",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product was not updated - server error"
            )
    })
    @PutMapping("/update")
    public void updateProduct(@RequestBody @Valid ProductDto productDto, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            productService.update(productDto);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Delete product",
            description = "Delete existed product from eshop",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product was not deleted - server error"
            )
    })
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable @Min(0) Integer id) {
        productService.delete(id);
    }

    @Operation(
            summary = "Find all category products",
            description = "Find all category products",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Products not fount - server error"
            )
    })
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable @Min(0) int id) {
        return new ResponseEntity<>(productService.getProductsByCategory(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Save products from file",
            description = "Save products from .csv file and persist to database",
            tags = {"product"})
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
    public ResponseEntity<ProductDto> loadFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity(productService.saveProductsFromFile(file), HttpStatus.OK);
    }

    @Operation(
            summary = "Save products to file",
            description = "Save products to .csv file ",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products was saved"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Products was not saved - server error"
            )
    })
    @GetMapping("/loadCsvFile")
    public void loadToFile(HttpServletResponse servletResponse) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        productService.saveProductsToFile(servletResponse);
    }

}
