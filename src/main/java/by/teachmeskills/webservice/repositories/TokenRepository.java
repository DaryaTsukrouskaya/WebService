package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByUsername(String username);
}
