package ua.dudka.service;

import ua.dudka.web.user.dto.MoneyTransferRequest;

/**
 * @author Rostislav Dudka
 */
public interface MoneyTransfer {
    void transfer(MoneyTransferRequest request);
}
