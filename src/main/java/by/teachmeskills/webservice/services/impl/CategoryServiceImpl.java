package by.teachmeskills.webservice.services.impl;


import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.impl.CategoryRepositoryImpl;
import by.teachmeskills.springbootproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepositoryImpl categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> read() throws DBConnectionException {
        return categoryRepository.read();
    }

    @Override
    public void create(Category category) throws DBConnectionException, UserAlreadyExistsException {
        categoryRepository.create(category);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        categoryRepository.delete(id);
    }


    public Category findById(int id) throws DBConnectionException {
        return categoryRepository.findById(id);
    }

    public ModelAndView getCategoriesData() throws DBConnectionException {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("categories", categoryRepository.read());
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath(), modelMap);

    }
}
