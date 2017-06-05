package ua.dudka.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dudka.employee.domain.Employee;

import java.util.Optional;

/**
 * @author Rostislav Dudka
 */

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findById(Integer id);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByPhoneNumber(String phoneNumber);
}