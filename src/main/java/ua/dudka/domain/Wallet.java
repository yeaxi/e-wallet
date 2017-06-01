package ua.dudka.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ua.dudka.exception.NotEnoughBalanceException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

import static ua.dudka.domain.Transaction.Type.WITHDRAWAL;

/**
 * @author Rostislav Dudka
 */
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class Wallet {

    @Id
    @GeneratedValue
    private Integer id = 0;

    private BigDecimal balance = BigDecimal.ZERO;

    private final Currency currency;

    public Wallet(BigDecimal balance, Currency currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public void applyTransaction(Transaction transaction) {
        BigDecimal transactionAmount = transaction.getAmount();
        if (transaction.getType() == WITHDRAWAL)
            withdraw(transactionAmount);
        else
            refill(transactionAmount);

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