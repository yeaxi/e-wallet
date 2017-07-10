package ua.dudka.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.service.CurrencyExchanger.ExchangeType;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyExchangeRequest {
    private BigDecimal amount;
    private Currency sellCurrency;
    private Currency buyCurrency;
    private ExchangeType exchangeType;
}