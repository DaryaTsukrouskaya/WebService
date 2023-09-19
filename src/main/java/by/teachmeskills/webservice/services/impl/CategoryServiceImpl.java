package by.teachmeskills.webservice.services.impl;


import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.dto.converters.CategoryConverter;
import by.teachmeskills.webservice.entities.Category;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import by.teachmeskills.webservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public List<CategoryDto> read() {
        return categoryRepository.read().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public void create(CategoryDto category) {
        categoryRepository.createOrUpdate(categoryConverter.fromDto(category));
    }

    @Override
    public void update(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryConverter.fromDto(categoryDto).getId());
        category.setName(categoryDto.getName());
        categoryRepository.createOrUpdate(category);
    }

    @Override
    public void delete(int id) {
        categoryRepository.delete(id);
    }

    @Override
    public CategoryDto findById(int id) {
        return categoryConverter.toDto(categoryRepository.findById(id));
    }
}
