package by.teachmeskills.webservice.controllers;

import by.teachmeskills.springbootproject.entities.KeyWords;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/search")
@SessionAttributes({"keyWords"})
public class ProductSearchController {
    private final ProductService productService;

    @Autowired
    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView getSearchPage() {
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView searchResult(@ModelAttribute("keyWords") KeyWords keyWords) throws DBConnectionException {
        return productService.searchProductsPaged(keyWords.getCurrentPageNumber(), keyWords.getKeyWords());
    }

    @GetMapping("/{pageNumber}")
    public ModelAndView certainSearchPage(@PathVariable int pageNumber, @SessionAttribute("keyWords") KeyWords keyWords) throws DBConnectionException {
        keyWords.setCurrentPageNumber(pageNumber);
        if (keyWords.getCurrentPageNumber() > 3) {
            keyWords.setCurrentPageNumber(keyWords.getCurrentPageNumber() - 1);
            pageNumber -= 1;
        }
        if (keyWords.getCurrentPageNumber() < 1) {
            keyWords.setCurrentPageNumber(keyWords.getCurrentPageNumber() + 1);
            pageNumber += 1;
        }
        return productService.searchProductsPaged(pageNumber, keyWords.getKeyWords());
    }

    @ModelAttribute("keyWords")
    public KeyWords setShoppingCart() {
        return new KeyWords();
    }
}
