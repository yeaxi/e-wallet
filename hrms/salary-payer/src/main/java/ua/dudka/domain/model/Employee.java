package ua.dudka.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Rostislav Dudka
 */
@Entity
@Getter
@EqualsAndHashCode(exclude = {"id", "company"})
@ToString(exclude = "company")
public class Employee {

    @Id
    private int id;
    private Salary salary;

    @ManyToOne
    @Setter(AccessLevel.PACKAGE)
    private Company company;

    public Employee() {
        salary = new Salary();
    }

    public Employee(int id, Salary salary) {
        this.id = id;
        this.salary = salary;
    }

    public void changeSalary(Salary salary) {
        this.salary = salary;
    }
}