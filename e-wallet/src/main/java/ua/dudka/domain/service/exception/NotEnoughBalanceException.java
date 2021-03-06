package ua.dudka.domain.service.exception;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
public class NotEnoughBalanceException extends MoneyTransferException {
    private static final String MESSAGE_FORMAT = "Not Enough balance! available balance: %s, withdrawal balance: %s";

    public NotEnoughBalanceException(BigDecimal amount, BigDecimal withdrawalAmount) {
        super(String.format(MESSAGE_FORMAT, amount, withdrawalAmount));
    }
}
