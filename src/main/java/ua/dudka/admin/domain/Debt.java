package ua.dudka.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Value;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Salary;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Embeddable
@Value
@AllArgsConstructor
public class Debt {
    private final int employeeId;
    private final Salary salary;

    Debt() {
        this.employeeId = 0;
        this.salary = Salary.of(BigDecimal.ZERO, Currency.UAH);
    }
}