package ua.dudka.account.domain.service;

import ua.dudka.account.web.dto.MoneyTransferRequest;

/**
 * @author Rostislav Dudka
 */
public interface MoneyTransfer {
    void transfer(MoneyTransferRequest request);
}
