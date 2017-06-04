package ua.dudka.employee.service;

import ua.dudka.employee.web.dto.MoneyTransferRequest;

/**
 * @author Rostislav Dudka
 */
public interface MoneyTransfer {
    void transfer(MoneyTransferRequest request);
}
