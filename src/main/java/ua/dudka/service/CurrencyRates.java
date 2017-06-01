package ua.dudka.service;

import ua.dudka.domain.Currency;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
public interface CurrencyRates {

    BigDecimal getRate(Currency sellCurrency, Currency buyCurrency);
}
