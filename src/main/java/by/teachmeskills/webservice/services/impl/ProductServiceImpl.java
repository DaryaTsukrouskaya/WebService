package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.dto.KeyWordsDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.dto.converters.ProductConverter;
import by.teachmeskills.webservice.entities.Category;
import by.teachmeskills.webservice.entities.Product;
import by.teachmeskills.webservice.repositories.ProductRepository;
import by.teachmeskills.webservice.repositories.impl.ProductRepositoryImpl;
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

    @Autowired
    public ProductServiceImpl(ProductRepositoryImpl productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }


    @Override
    public List<ProductDto> read() {
        return productRepository.read().stream().map(productConverter::toDto).toList();
    }

    @Override
    public void create(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(product.getDescription());
        productRepository.create(product);

    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
    }

    @Override
    public ProductDto findById(int id) {
        return productConverter.toDto(productRepository.findById(id));
    }

    @Override
    public List<ProductDto> getProductsByCategory(int id) {
        return productRepository.getProductsByCategory(id).stream().map(productConverter::toDto).toList();

    }

    @Override
    public void update(ProductDto productDto) {
        Product product = productRepository.findById(productConverter.fromDto(productDto).getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        productRepository.update(product);
    }

    @Override
    public List<ProductDto> searchProductsPaged(int pageNumber, KeyWordsDto keyWordsDto) {
        keyWordsDto.setCurrentPageNumber(pageNumber);
        if (keyWordsDto.getCurrentPageNumber() > 3) {
            keyWordsDto.setCurrentPageNumber(keyWordsDto.getCurrentPageNumber() - 1);
            pageNumber -= 1;
        }
        if (keyWordsDto.getCurrentPageNumber() < 1) {
            keyWordsDto.setCurrentPageNumber(keyWordsDto.getCurrentPageNumber() + 1);
            pageNumber += 1;
        }
        Long totalRecords;
        List<Product> products = null;
        int pageMaxResult;
        if (keyWordsDto.getKeyWords() != null) {
            totalRecords = productRepository.findProductsQuantityByKeywords(keyWordsDto.getKeyWords());
            pageMaxResult = (int) (totalRecords / 3);
            products = productRepository.findProductsByKeywords(keyWordsDto.getKeyWords(), pageNumber, pageMaxResult);
        }
        return products.stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<ProductDto> saveProductsFromFile(MultipartFile file) {
        List<ProductDto> csvProducts = parseCsv(file);
        List<Product> products = Optional.ofNullable(csvProducts).map(list -> list.stream().map(productConverter::fromDto).toList()).orElse(null);
        if (Optional.ofNullable(products).isPresent()) {
            products.forEach(productRepository::create);
            return products.stream().map(productConverter::toDto).toList();
        }
        return Collections.emptyList();
    }

    private List<ProductDto> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductDto> csvToBean = new CsvToBeanBuilder<ProductDto>(reader).withType(ProductDto.class).
                        withIgnoreLeadingWhiteSpace(true).withSeparator(',').build();
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
    public void saveProductsToFile(HttpServletResponse servletResponse) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<ProductDto> products = productRepository.read().stream().map(productConverter::toDto).toList();
        try (Writer writer = new OutputStreamWriter(servletResponse.getOutputStream())) {
            StatefulBeanToCsv<ProductDto> statefulBeanToCsv = new StatefulBeanToCsvBuilder<ProductDto>(writer).withSeparator(';').build();
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=" + "products.csv");
            statefulBeanToCsv.write(products);
        }

    }

}
