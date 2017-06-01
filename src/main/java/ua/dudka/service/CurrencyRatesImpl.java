package ua.dudka.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.domain.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rostislav Dudka
 */
@Service
public class CurrencyRatesImpl implements CurrencyRates {
    private final Map<Rate, BigDecimal> rates = new HashMap<>();


    public CurrencyRatesImpl() {
        rates.put(Rate.of(Currency.UAH, Currency.UAH), BigDecimal.valueOf(1));
        rates.put(Rate.of(Currency.UAH, Currency.USD), BigDecimal.valueOf(0.038));
        rates.put(Rate.of(Currency.UAH, Currency.EUR), BigDecimal.valueOf(0.034));
        rates.put(Rate.of(Currency.UAH, Currency.BTC), BigDecimal.valueOf(0.000017));

        rates.put(Rate.of(Currency.USD, Currency.USD), BigDecimal.valueOf(1));
        rates.put(Rate.of(Currency.USD, Currency.UAH), BigDecimal.valueOf(26.27));
        rates.put(Rate.of(Currency.USD, Currency.EUR), BigDecimal.valueOf(0.89));
        rates.put(Rate.of(Currency.USD, Currency.BTC), BigDecimal.valueOf(0.00045));

        rates.put(Rate.of(Currency.EUR, Currency.EUR), BigDecimal.valueOf(1));
        rates.put(Rate.of(Currency.EUR, Currency.UAH), BigDecimal.valueOf(29.55));
        rates.put(Rate.of(Currency.EUR, Currency.USD), BigDecimal.valueOf(1.23));
        rates.put(Rate.of(Currency.EUR, Currency.BTC), BigDecimal.valueOf(0.00051));

        rates.put(Rate.of(Currency.BTC, Currency.BTC), BigDecimal.valueOf(1));
        rates.put(Rate.of(Currency.BTC, Currency.UAH), BigDecimal.valueOf(58288.24));
        rates.put(Rate.of(Currency.BTC, Currency.USD), BigDecimal.valueOf(2215.23));
        rates.put(Rate.of(Currency.BTC, Currency.EUR), BigDecimal.valueOf(1971.28));
    }

    @Override
    public BigDecimal getRate(Currency sellCurrency, Currency buyCurrency) {
        return rates.get(Rate.of(sellCurrency, buyCurrency));
    }

    @RequiredArgsConstructor(staticName = "of")
    @Getter
    @EqualsAndHashCode
    private static class Rate {
        private final Currency sellCurrency;
        private final Currency buyCurrency;
    }
}
