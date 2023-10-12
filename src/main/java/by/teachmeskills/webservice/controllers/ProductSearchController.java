package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.dto.SearchParamsDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            summary = "Find products by keywords",
            description = "Find certain products which name or description contains keywords",
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
    @PostMapping("/byKeywords")
    public ResponseEntity<List<ProductDto>> searchByNameOrDescription(@RequestParam("keywords") String keywords, @RequestParam(name = "page", defaultValue = "0") int pageNumber, @RequestParam(name = "size", defaultValue = "2") int pageSize) {
        return new ResponseEntity<>(productService.searchProductsByKeyWords(keywords, pageNumber, pageSize), HttpStatus.OK);
    }

    @Operation(
            summary = "Find products by parameters",
            description = "Find products by specific parameters like price and category",
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
    @GetMapping("/advancedSearch")
    public ResponseEntity<List<ProductDto>> advancedSearch(@Valid @RequestBody SearchParamsDto searchParamsDto, @RequestParam(name = "page", defaultValue = "0") int pageNumber, @RequestParam(name = "size", defaultValue = "2") int pageSize, BindingResult result) throws ValidationException {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(productService.advancedSearch(searchParamsDto, pageNumber, pageSize), HttpStatus.OK);
        } else {
            throw new ValidationException(result.getFieldError().getDefaultMessage());
        }
    }

}
