package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);

    User findById(int id);
    Optional<User> findByLogin(String email);
}
