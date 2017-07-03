package ua.dudka.account.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ua.dudka.account.domain.model.Transaction.Type;
import ua.dudka.account.domain.model.vo.Transactions;
import ua.dudka.account.domain.service.exception.NotEnoughBalanceException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Rostislav Dudka
 */
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@EqualsAndHashCode(of = "id")
public class Wallet {

    @Id
    private String id = UUID.randomUUID().toString();

    private BigDecimal balance = BigDecimal.ZERO;

    private final Currency currency;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public Wallet(BigDecimal balance, Currency currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public Transactions getTransactions() {
        return new Transactions(this.transactions);
    }

    void withdraw(BigDecimal transactionAmount) {
        if (isNotEnoughBalance(transactionAmount)) {
            throw new NotEnoughBalanceException(this.balance, transactionAmount);
        }
        this.balance = this.balance.subtract(transactionAmount);
        transactions.add(new Transaction(transactionAmount, Type.WITHDRAWAL, balance));
    }

    private boolean isNotEnoughBalance(BigDecimal transactionAmount) {
        return this.balance.compareTo(transactionAmount) == -1;
    }

    void refill(BigDecimal transactionAmount) {
        balance = balance.add(transactionAmount);
        transactions.add(new Transaction(transactionAmount, Type.REFILL, balance));
    }
}