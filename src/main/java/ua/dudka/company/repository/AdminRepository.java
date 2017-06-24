package ua.dudka.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dudka.company.domain.Admin;

/**
 * @author Rostislav Dudka
 */
public interface AdminRepository extends JpaRepository<Admin, Integer> {
}