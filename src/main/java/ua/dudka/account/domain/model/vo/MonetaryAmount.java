package ua.dudka.account.domain.model.vo;

import lombok.Value;
import ua.dudka.account.domain.model.Currency;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Value(staticConstructor = "of")
public class MonetaryAmount {
    private BigDecimal amount;
    private Currency currency;
}