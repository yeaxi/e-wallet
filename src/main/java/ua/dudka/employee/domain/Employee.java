package ua.dudka.employee.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author Rostislav Dudka
 */
@Entity
@NoArgsConstructor(force = true)
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
    private String phoneNumber;
    private String position;

    @Embedded
    private Salary salary;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    private Employee(String name, String surname, String email, String phoneNumber, String position, Salary salary) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.salary = salary;
        this.account = new Account();
    }

    public void changeSalary(Salary salary) {
        this.salary = salary;
    }

    public static class EmployeeBuilder {

        public Employee build() {
            return new Employee(this.name, this.surname, this.email, this.phoneNumber, this.position, this.salary);
        }
    }

}