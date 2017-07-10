package ua.dudka.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Embeddable
@AllArgsConstructor
@Data
public class Salary {
    private BigDecimal amount;
    private Currency currency;

    public Salary() {
        this.amount = BigDecimal.ZERO;
        this.currency = Currency.UAH;
    }

    public static Salary of(BigDecimal amount, Currency currency) {
        return new Salary(amount, currency);
    }
}