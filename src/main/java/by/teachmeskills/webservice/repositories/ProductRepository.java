package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.Product;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ProductRepository {
    void createOrUpdate(Product product) throws EntityNotFoundException;

    void delete(int id);

    List<Product> read();

    Product findById(int id);

    List<Product> getProductsByCategory(int id);

    List<Product> findProductsByKeywords(String words, int pageNumber, int maxResult);

    Long findProductsQuantityByKeywords(String words);

}
