package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.dto.SearchParamsDto;
import by.teachmeskills.webservice.dto.converters.ProductConverter;
import by.teachmeskills.webservice.entities.Product;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import by.teachmeskills.webservice.repositories.ProductRepository;
import by.teachmeskills.webservice.repositories.ProductSearchSpecification;
import by.teachmeskills.webservice.services.ProductService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<ProductDto> read() {
        return productRepository.findAll().stream().map(productConverter::toDto).toList();
    }

    @Override
    public void create(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(product.getDescription());
        product.setImagePath(product.getImagePath());
        product.setCategory(categoryRepository.findById(productDto.getCategoryId()));
        productRepository.save(product);

    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto findById(int id) {
        return productConverter.toDto(productRepository.findById(id));
    }

    @Override
    public List<ProductDto> getProductsByCategory(int id) {
        return productRepository.findByCategoryId(id).stream().map(productConverter::toDto).toList();

    }

    @Override
    public void update(ProductDto productDto) {
        Product product = productRepository.findById(productConverter.fromDto(productDto).getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImagePath(productDto.getImagePath());
        product.setCategory(categoryRepository.findById(productDto.getCategoryId()));
        productRepository.save(product);
    }

    @Override
    public List<ProductDto> saveProductsFromFile(MultipartFile file) {
        List<ProductDto> csvProducts = parseCsv(file);
        List<Product> products = Optional.ofNullable(csvProducts).map(list -> list.stream().map(productConverter::fromDto).toList()).orElse(null);
        if (Optional.ofNullable(products).isPresent()) {
            products.forEach(productRepository::save);
            return products.stream().map(productConverter::toDto).toList();
        }
        return Collections.emptyList();
    }

    private List<ProductDto> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductDto> csvToBean = new CsvToBeanBuilder<ProductDto>(reader).withType(ProductDto.class).
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
    public void saveProductsToFile(HttpServletResponse servletResponse, int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<ProductDto> products = productRepository.findByCategoryId(id).stream().map(productConverter::toDto).toList();
        try (Writer writer = new OutputStreamWriter(servletResponse.getOutputStream())) {
            StatefulBeanToCsv<ProductDto> statefulBeanToCsv = new StatefulBeanToCsvBuilder<ProductDto>(writer).withSeparator(';').build();
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=" + "products.csv");
            statefulBeanToCsv.write(products);
        }

    }

    @Override
    public List<ProductDto> getProductsByCategoryPaged(int id, int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        List<Product> productList = productRepository.findByCategoryId(id, pageable).getContent();
        if (productList.isEmpty()) {
            pageNumber -= pageNumber;
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
            productList = productRepository.findByCategoryId(id, pageable).getContent();
        }
        return productList.stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<ProductDto> searchProductsByKeyWords(String keywords, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        List<Product> products = productRepository.findByKeyWords(keywords, pageable).getContent();
        return products.stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<ProductDto> advancedSearch(SearchParamsDto searchParamsDto, int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        ProductSearchSpecification specification = new ProductSearchSpecification(searchParamsDto);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        List<Product> products = productRepository.findAll(specification, pageable).getContent();
        if (products.isEmpty() && pageNumber > 0) {
            pageNumber -= 1;
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
            products = productRepository.findAll(specification, pageable).getContent();
        }
        return products.stream().map(productConverter::toDto).toList();
    }
}
