package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.ProductDto;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ProductService {
    List<ProductDto> read();

    void create(ProductDto product);

    void delete(int id);

    void update(ProductDto productDto);

    ProductDto findById(int id);

    List<ProductDto> getProductsByCategory(int id);

    List<ProductDto> searchProductsPaged(int pageNumber, String keyWords);
}
