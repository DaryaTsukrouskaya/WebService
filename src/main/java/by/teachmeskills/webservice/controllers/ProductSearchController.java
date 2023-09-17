package by.teachmeskills.webservice.controllers;


import by.teachmeskills.webservice.dto.KeyWordsDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.exceptions.ValidationException;
import by.teachmeskills.webservice.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/searchResult")
    public ResponseEntity<List<ProductDto>> searchResult(@RequestBody @Valid KeyWordsDto keyWords, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            return new ResponseEntity<>(productService.searchProductsPaged(keyWords.getCurrentPageNumber(), keyWords.getKeyWords()), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<List<ProductDto>> certainSearchPage(@PathVariable int pageNumber, @Valid KeyWordsDto keyWords, BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) {
            keyWords.setCurrentPageNumber(pageNumber);
            if (keyWords.getCurrentPageNumber() > 3) {
                keyWords.setCurrentPageNumber(keyWords.getCurrentPageNumber() - 1);
                pageNumber -= 1;
            }
            if (keyWords.getCurrentPageNumber() < 1) {
                keyWords.setCurrentPageNumber(keyWords.getCurrentPageNumber() + 1);
                pageNumber += 1;
            }
            return new ResponseEntity<>(productService.searchProductsPaged(pageNumber, keyWords.getKeyWords()), HttpStatus.OK);
        } else {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
