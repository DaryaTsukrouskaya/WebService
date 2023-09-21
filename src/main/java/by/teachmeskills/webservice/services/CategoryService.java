package by.teachmeskills.webservice.services;


import by.teachmeskills.webservice.dto.CategoryDto;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> read();

    void create(CategoryDto category);

    List<CategoryDto> saveCategoriesFromFile(MultipartFile file);

    void delete(int id);

    void update(CategoryDto categoryDto);

    CategoryDto findById(int id);

    void saveCategoriesToFile(HttpServletResponse servletResponse) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}
