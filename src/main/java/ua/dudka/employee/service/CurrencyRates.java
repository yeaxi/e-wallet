package ua.dudka.employee.service;

import ua.dudka.employee.domain.Currency;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
public interface CurrencyRates {

    BigDecimal getRate(Currency sellCurrency, Currency buyCurrency);
}
