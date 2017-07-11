package ua.dudka.application.event.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ua.dudka.application.event.dto.TransferMoneyRequest;

import static ua.dudka.application.event.channel.SalaryPayerChannels.TRANSFER_MONEY_CHANNEL_NAME;

/**
 * @author Rostislav Dudka
 */
@MessagingGateway
public interface TransferMoneyRequestSender {

    @Gateway(requestChannel = TRANSFER_MONEY_CHANNEL_NAME)
    void send(TransferMoneyRequest request);
}