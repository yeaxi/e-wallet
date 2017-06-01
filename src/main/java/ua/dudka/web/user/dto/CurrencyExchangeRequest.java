package ua.dudka.web.user.dto;

import lombok.*;
import ua.dudka.domain.Currency;
import ua.dudka.service.CurrencyExchanger.ExchangeType;

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
