package ua.dudka.hrm.domain.model.company;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.domain.model.vo.MonetaryAmount;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.domain.model.employee.Salary;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Company {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account = new Account();

    public Company(BigDecimal initialBalance) {
        this.account = new Account(initialBalance);
    }

    public void paySalary(Employee employee) {
        Salary salary = employee.getSalary();

        MonetaryAmount amount = MonetaryAmount.of(salary.getAmount(), salary.getCurrency());
        account.withdraw(amount);
        employee.getAccount().refill(amount);
    }
}