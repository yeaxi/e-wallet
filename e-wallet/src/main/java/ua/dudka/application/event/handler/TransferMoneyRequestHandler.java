package ua.dudka.application.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import ua.dudka.domain.service.MoneyTransfer;
import ua.dudka.web.dto.MoneyTransferRequest;

import static ua.dudka.application.event.channel.EWalletChannels.TRANSFER_MONEY_CHANNEL_NAME;

/**
 * @author Rostislav Dudka
 */
@Component
@RequiredArgsConstructor
@Slf4j
class TransferMoneyRequestHandler {

    private final MoneyTransfer moneyTransfer;

    @StreamListener(TRANSFER_MONEY_CHANNEL_NAME)
    void handle(MoneyTransferRequest request) {
        log.debug("handling event {}", request);
        try {
            moneyTransfer.transfer(request);
        } catch (Exception e) {
            log.error("undesirable err {}", e.getMessage());
        }
    }
}