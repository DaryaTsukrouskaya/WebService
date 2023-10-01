package by.teachmeskills.webservice.dto.converters;

import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.entities.Product;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import by.teachmeskills.webservice.repositories.ProductRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductConverter {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductConverter(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public ProductDto toDto(Product product) {
        return Optional.ofNullable(product).map(p -> ProductDto.builder().id(p.getId()).name(p.getName()).
                description(p.getDescription()).imagePath(p.getImagePath()).categoryId(p.getCategory().getId()).price(p.getPrice()).build()).orElse(null);
    }

    public Product fromDto(ProductDto productDto) {
        return Optional.ofNullable(productDto).map(p -> Product.builder().id(p.getId()).name(p.getName()).
                description(p.getDescription()).imagePath(p.getImagePath()).category(categoryRepository.findById(p.getCategoryId())).price(p.getPrice()).build()).orElse(null);
    }
}
