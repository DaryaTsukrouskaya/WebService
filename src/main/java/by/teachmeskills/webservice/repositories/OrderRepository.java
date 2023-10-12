package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(int id);

    Page<Order> findByUserId(int id, Pageable page);

    Order findById(int id);
}
