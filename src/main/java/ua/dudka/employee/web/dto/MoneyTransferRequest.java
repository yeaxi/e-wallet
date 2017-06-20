package ua.dudka.employee.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dudka.employee.domain.Currency;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MoneyTransferRequest {
    private BigDecimal amount;
    private Currency currency;
    private int destinationAccountNumber;

}