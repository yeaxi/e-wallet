package ua.dudka.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Embeddable
@AllArgsConstructor(staticName = "of")
@Value
public class Salary {
    BigDecimal amount;
    Currency currency;

    Salary() {
        this.amount = BigDecimal.ZERO;
        this.currency = Currency.UAH;
    }

    //why JPA, why???
    void setAmount(BigDecimal amount) {
    }

    void setCurrency(Currency currency) {

    }
}