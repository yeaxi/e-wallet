package ua.dudka.application.event;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * @author Rostislav Dudka
 */
@MessagingGateway
public interface TransferMoneyRequestSender {

    @Gateway(requestChannel = SalaryPayerChannels.TRANSFER_MONEY_CHANNEL_NAME)
    void send(TransferMoneyRequest request);
}