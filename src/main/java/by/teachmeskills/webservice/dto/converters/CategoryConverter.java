package by.teachmeskills.webservice.dto.converters;

import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.entities.Category;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryConverter {
    private final ProductConverter productConverter;

    public CategoryConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public CategoryDto toDto(Category category) {
        return Optional.ofNullable(category).map(c -> CategoryDto.builder().id(c.getId()).name(c.getName()).
                products(Optional.ofNullable(c.getProducts()).map(p -> p.stream().map(productConverter::toDto).toList()).orElse(List.of())).
                build()).orElse(null);
    }
    public Category fromDto(CategoryDto category) {
        return Optional.ofNullable(category).map(c -> Category.builder().id(c.getId()).name(c.getName()).
                products(Optional.ofNullable(c.getProducts()).map(p -> p.stream().map(productConverter::fromDto).toList()).orElse(List.of())).
                build()).orElse(null);
    }
}
