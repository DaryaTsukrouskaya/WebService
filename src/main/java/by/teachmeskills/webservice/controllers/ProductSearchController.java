package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.KeyWordsDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/search")
@Tag(name = "search", description = "Products search endpoints")
public class ProductSearchController {
    private final ProductService productService;

    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Find certain products",
            description = "Get first search result of products",
            tags = {"search"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products was found",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Products not found - server error"
            )
    })
    @PostMapping("/searchResult")
    public ResponseEntity<List<ProductDto>> searchResult(@RequestBody @Valid KeyWordsDto keyWords, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(productService.searchProductsPaged(keyWords.getCurrentPageNumber(), keyWords), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Operation(
            summary = "Find certain products",
            description = "Get search result of products according to chosen page",
            tags = {"search"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products was found",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Products not found - server error"
            )
    })
    @GetMapping("/{pageNumber}")
    public ResponseEntity<List<ProductDto>> certainSearchPage(@PathVariable int pageNumber, @Valid @RequestBody KeyWordsDto keyWords, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(productService.searchProductsPaged(pageNumber, keyWords), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
