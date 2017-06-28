package ua.dudka.account.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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

    public void applyTransaction(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        Transaction.Type type = transaction.getType();
        if (type == Transaction.Type.WITHDRAWAL)
            withdraw(amount);
        else
            refill(amount);

        transactions.add(new Transaction(amount, type, currency, balance));

    }

    private void withdraw(BigDecimal transactionAmount) {
        if (isNotEnoughBalance(transactionAmount)) {
            throw new NotEnoughBalanceException(this.balance, transactionAmount);
        }
        this.balance = this.balance.subtract(transactionAmount);
    }

    private boolean isNotEnoughBalance(BigDecimal transactionAmount) {
        return this.balance.compareTo(transactionAmount) == -1;
    }

    private void refill(BigDecimal transactionAmount) {
        this.balance = this.balance.add(transactionAmount);
    }
}