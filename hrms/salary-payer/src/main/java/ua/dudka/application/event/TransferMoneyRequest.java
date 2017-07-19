package ua.dudka.application.event;

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
public class TransferMoneyRequest {
    private BigDecimal amount;
    private Currency currency;
    private int sourceAccountNumber;
    private int destinationAccountNumber;
}