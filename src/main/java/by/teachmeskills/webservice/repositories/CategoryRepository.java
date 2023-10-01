package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.Category;


import java.util.List;

public interface CategoryRepository {
    Category update(Category category);

    Category create(Category category);

    void delete(int id);

    List<Category> read();

    Category findById(int id);
}
