package ua.dudka.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dudka.domain.model.Currency;

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
    private int sourceAccountNumber;
    private int destinationAccountNumber;
}