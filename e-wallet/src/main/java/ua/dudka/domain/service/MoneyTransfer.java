package ua.dudka.domain.service;

import ua.dudka.web.dto.MoneyTransferRequest;

/**
 * @author Rostislav Dudka
 */
public interface MoneyTransfer {
    void transfer(MoneyTransferRequest request);
}
