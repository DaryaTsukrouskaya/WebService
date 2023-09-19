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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
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
    public void create(ProductDto product) {
        productRepository.createOrUpdate(productConverter.fromDto(product));

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
        productRepository.createOrUpdate(product);
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
}
