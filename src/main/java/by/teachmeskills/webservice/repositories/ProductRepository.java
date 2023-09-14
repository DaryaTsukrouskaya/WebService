package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.Product;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    Product findById(int id) throws DBConnectionException;

    List<Product> getProductsByCategory(int id) throws DBConnectionException;

    List<Product> findProductsByKeywords(String words, int pageNumber, int maxResult) throws DBConnectionException;

    Long findProductsQuantityByKeywords(String words) throws DBConnectionException;

}
