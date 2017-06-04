package ua.dudka.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dudka.admin.domain.Admin;

/**
 * @author Rostislav Dudka
 */
public interface AdminRepository extends JpaRepository<Admin, Integer> {
}