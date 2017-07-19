package ua.dudka.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rostislav Dudka
 */
@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(exclude = "employees")
@ToString
public class Company {

    @Id
    @GeneratedValue
    private int id;

    private String email;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    public Company(String email) {
        this.email = email;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }
}