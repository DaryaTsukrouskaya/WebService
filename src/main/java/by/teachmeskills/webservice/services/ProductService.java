package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.dto.SearchParamsDto;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductDto> read();

    void create(ProductDto product);

    List<ProductDto> saveProductsFromFile(MultipartFile file);

    void delete(int id);

    void update(ProductDto productDto);

    void saveProductsToFile(HttpServletResponse servletResponse, int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;

    ProductDto findById(int id);

    List<ProductDto> getProductsByCategory(int id);

    List<ProductDto> getProductsByCategoryPaged(int id, int pageNumber, int pageSize);

    List<ProductDto> searchProductsByKeyWords(String keyWords, int pageNumber, int pageSize);

    List<ProductDto> advancedSearch(SearchParamsDto searchParamsDto, int pageNumber, int pageSize);
}
