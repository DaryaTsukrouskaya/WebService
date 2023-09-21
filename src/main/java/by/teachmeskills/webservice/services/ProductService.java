package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.KeyWordsDto;
import by.teachmeskills.webservice.dto.ProductDto;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductDto> read();

    void create(ProductDto product);

    void delete(int id);

    void update(ProductDto productDto);

    ProductDto findById(int id);

    List<ProductDto> getProductsByCategory(int id);

    List<ProductDto> searchProductsPaged(int pageNumber, KeyWordsDto keyWords);
}
