package ua.dudka.account.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.account.domain.model.vo.Transactions;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rostislav Dudka
 */
@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "number")
@ToString
public class Account {

    private static final int RECENT_TRANSACTIONS_AMOUNT = 10;

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer number = 0;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet uahWallet = new Wallet(Currency.UAH);
    @OneToOne(cascade = CascadeType.ALL)
    private Wallet usdWallet = new Wallet(Currency.USD);
    @OneToOne(cascade = CascadeType.ALL)
    private Wallet eurWallet = new Wallet(Currency.EUR);
    @OneToOne(cascade = CascadeType.ALL)
    private Wallet btcWallet = new Wallet(Currency.BTC);


    @OneToOne()
    private Employee employee;

    public Account(BigDecimal initialBalance) {
        this.uahWallet = new Wallet(initialBalance, Currency.UAH);
        this.usdWallet = new Wallet(initialBalance, Currency.USD);
        this.eurWallet = new Wallet(initialBalance, Currency.EUR);
        this.btcWallet = new Wallet(initialBalance, Currency.BTC);
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();

    private Transactions getTransactions() {
        return new Transactions(this.transactions);
    }

    public List<Transaction> getRecentTransactions() {
        Transactions transactions = getTransactions();
        if (transactions.size() <= RECENT_TRANSACTIONS_AMOUNT)
            return transactions;
        return transactions.subList(0, RECENT_TRANSACTIONS_AMOUNT);
    }


    public void applyTransaction(Transaction transaction) {
        Wallet wallet = getWalletByCurrency(transaction.getCurrency());
        wallet.applyTransaction(transaction);

        transactions.add(new Transaction(transaction.getAmount(), transaction.getType(), transaction.getCurrency(), wallet.getBalance()));
    }

    public Wallet getWalletByCurrency(Currency currency) {
        switch (currency) {
            case UAH:
                return this.uahWallet;
            case USD:
                return this.usdWallet;
            case EUR:
                return this.eurWallet;
            case BTC:
                return this.btcWallet;
            default:
                return this.uahWallet;
        }
    }
}