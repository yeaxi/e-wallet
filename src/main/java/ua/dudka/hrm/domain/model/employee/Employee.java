package ua.dudka.hrm.domain.model.employee;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ua.dudka.account.domain.model.Account;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Rostislav Dudka
 */
@Entity
@Getter
@EqualsAndHashCode(exclude = {"id", "account"})
@ToString
@Builder
public class Employee {

    @Id
    @GeneratedValue
    private int id;
    private final String name;
    private final String surname;
    private final String email;
    private String password;
    private String phoneNumber;
    private String position;
    private final LocalDate enrollDate;
    private Salary salary;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    public Employee() {
        name = "";
        surname = "";
        email = "";
        password = "";
        phoneNumber = "";
        position = "";
        enrollDate = LocalDate.now();
        salary = new Salary();
        account = new Account();
    }

    private Employee(String name, String surname, String email, String phoneNumber, String position, Salary salary) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = phoneNumber;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.salary = salary;
        this.account = new Account();
        this.enrollDate = LocalDate.now();
    }

    public void changeSalary(Salary salary) {
        this.salary = salary;
    }

    public void changePosition(String newPosition) {
        this.position = newPosition;
    }

    public static class EmployeeBuilder {
        public Employee build() {
            return new Employee(this.name, this.surname, this.email, this.phoneNumber, this.position, this.salary);
        }
    }

}