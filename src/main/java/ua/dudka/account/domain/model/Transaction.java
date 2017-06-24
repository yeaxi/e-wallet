package ua.dudka.account.domain.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Rostislav Dudka
 */
@Embeddable
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@Getter
@EqualsAndHashCode(exclude = {"number","date"})
@ToString
public class Transaction {

    private final String number = UUID.randomUUID().toString();
    private final BigDecimal amount;
    private final LocalDateTime date = LocalDateTime.now();
    private final Type type;
    private final Currency currency;
    private final BigDecimal balance;


    public Transaction(BigDecimal amount, Type type, Currency currency) {
        this.amount = amount;
        this.type = type;
        this.currency = currency;
        this.balance = BigDecimal.ZERO;
    }

    public enum Type {
        WITHDRAWAL, REFILL
    }
}