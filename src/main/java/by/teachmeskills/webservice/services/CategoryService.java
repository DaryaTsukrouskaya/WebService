package by.teachmeskills.webservice.services;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;

public interface CategoryService extends BaseService<Category> {
    Category findById(int id) throws DBConnectionException;
}
