package ua.dudka.hrm.domain.model.company;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.domain.model.Transaction;
import ua.dudka.hrm.domain.model.employee.Employee;

import javax.persistence.*;
import java.math.BigDecimal;

import static ua.dudka.account.domain.model.Transaction.Type.REFILL;
import static ua.dudka.account.domain.model.Transaction.Type.WITHDRAWAL;

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
        Currency currency = employee.getSalary().getCurrency();
        BigDecimal salaryAmount = employee.getSalary().getAmount();

        account.applyTransaction(new Transaction(salaryAmount, WITHDRAWAL, currency));
        employee.getAccount().applyTransaction(new Transaction(salaryAmount, REFILL, currency));
    }
}