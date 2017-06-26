package ua.dudka.account.domain.service;

import org.junit.Test;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.domain.service.CurrencyRates;
import ua.dudka.account.domain.service.impl.CurrencyRatesImpl;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author Rostislav Dudka
 */

public class CurrencyRatesTest {

    private CurrencyRates currencyRates = new CurrencyRatesImpl();

    @Test
    public void getRateShouldReturnRateForDifferentCurrencies() throws Exception {
        BigDecimal rate = currencyRates.getRate(Currency.UAH, Currency.USD);
        assertNotNull(rate);
        assertFalse(rate.toPlainString().isEmpty());
    }

    @Test
    public void getRateShouldReturnRateForSameCurrencies() throws Exception {
        BigDecimal rate = currencyRates.getRate(Currency.UAH, Currency.UAH);

        assertNotNull(rate);
        assertFalse(rate.toPlainString().isEmpty());
    }

}