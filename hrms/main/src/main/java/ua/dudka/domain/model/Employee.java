package ua.dudka.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * @author Rostislav Dudka
 */
@Entity
@Getter
@EqualsAndHashCode(exclude = {"id", "company"})
@ToString(exclude = "company")
@Builder
public class Employee {

    @Id
    @GeneratedValue
    private int id;
    private final String name;
    private final String surname;
    private final String email;
    private String phoneNumber;
    private String position;
    private final LocalDate enrollDate;
    private Salary salary;

    @ManyToOne
    private Company company;

    public Employee() {
        name = "";
        surname = "";
        email = "";
        phoneNumber = "";
        position = "";
        enrollDate = LocalDate.now();
        salary = new Salary();
    }

    private Employee(String name, String surname, String email,
                     String phoneNumber, String position, Salary salary, Company company) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.salary = salary;
        this.enrollDate = LocalDate.now();
        this.company = company;
    }

    public void changeSalary(Salary salary) {
        this.salary = salary;
    }

    public void changePosition(String newPosition) {
        this.position = newPosition;
    }

    public static class EmployeeBuilder {
        public Employee build() {
            return new Employee(this.name, this.surname, this.email, this.phoneNumber, this.position, this.salary, this.company);
        }
    }

}