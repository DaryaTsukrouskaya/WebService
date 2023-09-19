package by.teachmeskills.webservice.controllers;


import by.teachmeskills.webservice.dto.KeyWordsDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.ProductService;
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
public class ProductSearchController {
    private final ProductService productService;

    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/searchResult")
    public ResponseEntity<List<ProductDto>> searchResult(@RequestBody @Valid KeyWordsDto keyWords, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(productService.searchProductsPaged(keyWords.getCurrentPageNumber(), keyWords), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<List<ProductDto>> certainSearchPage(@PathVariable int pageNumber, @Valid @RequestBody KeyWordsDto keyWords, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(productService.searchProductsPaged(pageNumber, keyWords), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
