package by.teachmeskills.webservice.services;


import by.teachmeskills.webservice.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> read();

    void create(CategoryDto category);

    void delete(int id);

    void update(CategoryDto categoryDto);

    CategoryDto findById(int id);
}
