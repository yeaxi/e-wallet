package ua.dudka.employee.service;

import ua.dudka.employee.web.dto.CurrencyExchangeRequest;

/**
 * @author Rostislav Dudka
 */
public interface CurrencyExchanger {

    void exchange(CurrencyExchangeRequest request);

    enum ExchangeType {
        SELL, BUY
    }
}
