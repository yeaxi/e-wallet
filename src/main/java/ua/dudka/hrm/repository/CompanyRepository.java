package ua.dudka.hrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dudka.hrm.domain.model.company.Company;

/**
 * @author Rostislav Dudka
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}