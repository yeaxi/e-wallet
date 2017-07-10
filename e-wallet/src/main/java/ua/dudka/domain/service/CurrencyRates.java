package ua.dudka.domain.service;

import ua.dudka.domain.model.Currency;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
public interface CurrencyRates {

    BigDecimal getRate(Currency sellCurrency, Currency buyCurrency);
}
