package ua.dudka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dudka.domain.model.Company;

/**
 * @author Rostislav Dudka
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}