package ua.dudka.application.event.sender;

import ua.dudka.application.event.dto.TransferMoneyRequest;

/**
 * @author Rostislav Dudka
 */
public interface TransferMoneyRequestSender {

    void send(TransferMoneyRequest request);
}