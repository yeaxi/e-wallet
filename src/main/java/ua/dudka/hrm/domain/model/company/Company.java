package ua.dudka.hrm.domain.model.company;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import ua.dudka.account.domain.model.Transaction;
import ua.dudka.account.domain.model.Wallet;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.domain.model.employee.Salary;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static org.hibernate.annotations.LazyCollectionOption.FALSE;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = EAGER)
    private List<Wallet> wallets = new ArrayList<>();

    @LazyCollection(FALSE)
    @ElementCollection
    private List<Debt> debts = new ArrayList<>();


    public Company(BigDecimal initialBalance) {
        this.wallets.add(new Wallet(initialBalance, Currency.UAH));
        this.wallets.add(new Wallet(initialBalance, Currency.USD));
        this.wallets.add(new Wallet(initialBalance, Currency.EUR));
        this.wallets.add(new Wallet(initialBalance, Currency.BTC));
    }

    public void paySalary(Employee employee) {
        Salary salary = employee.getSalary();

        if (isEnoughBalance(salary))
            pay(employee);
        else
            addDebt(employee);

    }

    private boolean isEnoughBalance(Salary salary) {
        Wallet wallet = getWalletByCurrency(salary.getCurrency());

        return wallet.getBalance().compareTo(salary.getAmount()) != -1;
    }

    private void addDebt(Employee employee) {
        Debt debt = new Debt(employee.getId(), employee.getSalary());
        debts.add(debt);
    }


    private void pay(Employee employee) {
        Currency currency = employee.getSalary().getCurrency();
        BigDecimal salaryAmount = employee.getSalary().getAmount();
        Wallet wallet = getWalletByCurrency(currency);

        wallet.applyTransaction(new Transaction(salaryAmount, WITHDRAWAL, currency));
        employee.getAccount().applyTransaction(new Transaction(salaryAmount, REFILL, currency));
    }

    public Wallet getWalletByCurrency(Currency currency) {
        return wallets.stream()
                .filter(w -> w.getCurrency().equals(currency))
                .findFirst()
                .orElse(new Wallet(BigDecimal.ZERO, currency));
    }

}