package ua.dudka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dudka.domain.model.Account;

import java.util.Optional;

/**
 * @author Rostislav Dudka
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByNumber(Integer number);

    Optional<Account> findByEmail(String email);
}