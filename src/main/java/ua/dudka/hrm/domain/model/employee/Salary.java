package ua.dudka.hrm.domain.model.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.dudka.account.domain.model.Currency;

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

    Salary() {
        this.amount = BigDecimal.ZERO;
        this.currency = Currency.UAH;
    }

    public static Salary of(BigDecimal amount, Currency currency) {
        return new Salary(amount, currency);
    }
}