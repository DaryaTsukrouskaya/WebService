package by.teachmeskills.webservice.services.impl;


import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.dto.converters.CategoryConverter;
import by.teachmeskills.webservice.dto.converters.ProductConverter;
import by.teachmeskills.webservice.entities.Category;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import by.teachmeskills.webservice.services.CategoryService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final ProductConverter productConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter, ProductConverter productConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.productConverter = productConverter;
    }

    @Override
    public List<CategoryDto> read() {
        return categoryRepository.read().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public void create(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(category.getName());
        category.setProducts(categoryDto.getProducts().stream().map(productConverter::fromDto).toList());
        categoryRepository.create(category);
    }

    @Override
    public void update(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryConverter.fromDto(categoryDto).getId());
        category.setName(categoryDto.getName());
        categoryRepository.update(category);
    }

    @Override
    public void delete(int id) {
        categoryRepository.delete(id);
    }

    @Override
    public CategoryDto findById(int id) {
        return categoryConverter.toDto(categoryRepository.findById(id));
    }

    @Override
    public List<CategoryDto> saveCategoriesFromFile(MultipartFile file) {
        List<CategoryDto> csvCategories = parseCsv(file);
        List<Category> categories = Optional.ofNullable(csvCategories).map(list -> list.stream().map(categoryConverter::fromDto).toList()).orElse(null);
        if (Optional.ofNullable(categories).isPresent()) {
            categories.forEach(categoryRepository::create);
            return categories.stream().map(categoryConverter::toDto).toList();
        }
        return Collections.emptyList();
    }

    private List<CategoryDto> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<CategoryDto> csvToBean = new CsvToBeanBuilder<CategoryDto>(reader).withType(CategoryDto.class).
                        withIgnoreLeadingWhiteSpace(true).withSeparator(';').build();
                return csvToBean.parse();
            } catch (IOException e) {
                log.error("Exception occurred during csv parsing:" + e.getMessage());
            }
        } else {
            log.error("Empty scv file is uploaded");
        }
        return Collections.emptyList();
    }

    @Override
    public void saveCategoriesToFile(HttpServletResponse servletResponse) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<CategoryDto> categories = categoryRepository.read().stream().map(categoryConverter::toDto).toList();
        try (Writer writer = new OutputStreamWriter(servletResponse.getOutputStream())) {
            StatefulBeanToCsv<CategoryDto> statefulBeanToCsv = new StatefulBeanToCsvBuilder<CategoryDto>(writer).withSeparator(';').build();
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=" + "categories.csv");
            statefulBeanToCsv.write(categories);
        }
    }
}
