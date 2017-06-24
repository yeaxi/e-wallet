package ua.dudka.account.domain.service;

import ua.dudka.account.domain.model.Currency;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
public interface CurrencyRates {

    BigDecimal getRate(Currency sellCurrency, Currency buyCurrency);
}
