package ua.dudka.employee.web.dto;

import lombok.*;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.service.CurrencyExchanger.ExchangeType;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@EqualsAndHashCode
@ToString
public class CurrencyExchangeRequest {
    private final BigDecimal amount;
    private final Currency sellCurrency;
    private final Currency buyCurrency;
    private final ExchangeType exchangeType;
}
