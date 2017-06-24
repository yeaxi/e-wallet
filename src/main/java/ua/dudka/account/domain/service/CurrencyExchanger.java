package ua.dudka.account.domain.service;

import ua.dudka.account.web.dto.CurrencyExchangeRequest;

/**
 * @author Rostislav Dudka
 */
public interface CurrencyExchanger {

    void exchange(CurrencyExchangeRequest request);

    enum ExchangeType {
        SELL, BUY
    }
}
