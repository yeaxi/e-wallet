package ua.dudka.employee.web.dto;

import lombok.*;
import ua.dudka.employee.domain.Currency;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MoneyTransferRequest {
    private final BigDecimal amount;
    private final Currency currency;
    private final int destinationAccountNumber;

}
