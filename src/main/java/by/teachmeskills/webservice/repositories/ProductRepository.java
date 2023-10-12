package by.teachmeskills.webservice.repositories;


import by.teachmeskills.webservice.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    List<Product> findByCategoryId(int id);

    Page<Product> findByCategoryId(int id, Pageable page);

    Product findById(int id);

    @Query("select p from Product p where p.name like %:keywords% or p.description like %:keywords%")
    Page<Product> findByKeyWords(@Param("keywords") String keywords, Pageable pageable);

}
