package ua.dudka.domain.service;

import ua.dudka.web.dto.CurrencyExchangeRequest;

/**
 * @author Rostislav Dudka
 */
public interface CurrencyExchanger {

    void exchange(CurrencyExchangeRequest request);

    enum ExchangeType {
        SELL, BUY
    }
}
