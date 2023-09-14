package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.Category;

public interface CategoryRepository {
    Category findById(int id) throws DBConnectionException;
}
