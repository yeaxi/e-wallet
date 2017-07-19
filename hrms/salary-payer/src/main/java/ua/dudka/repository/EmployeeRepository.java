package ua.dudka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dudka.domain.model.Employee;

import java.util.Optional;

/**
 * @author Rostislav Dudka
 */

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}