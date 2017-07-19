package ua.dudka.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

/**
 * @author Rostislav Dudka
 */
@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(exclude = "employees")
@ToString
public class Company {

    @Id
    private final int id;

    @OneToMany(mappedBy = "company", fetch = EAGER, cascade = {ALL})
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee) {
        employee.setCompany(this);
        this.employees.add(employee);
    }
}