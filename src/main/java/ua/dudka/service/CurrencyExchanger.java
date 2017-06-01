package ua.dudka.service;

import ua.dudka.web.user.dto.CurrencyExchangeRequest;

/**
 * @author Rostislav Dudka
 */
public interface CurrencyExchanger {

    void exchange(CurrencyExchangeRequest request);

    enum ExchangeType {
        SELL, BUY
    }
}
