package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.Category;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface CategoryRepository {
    void createOrUpdate(Category category);

    void delete(int id);

    List<Category> read();

    Category findById(int id);
}
