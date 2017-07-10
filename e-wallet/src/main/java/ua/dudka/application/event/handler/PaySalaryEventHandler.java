package ua.dudka.application.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ua.dudka.domain.service.MoneyTransfer;
import ua.dudka.web.dto.MoneyTransferRequest;

/**
 * @author Rostislav Dudka
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PaySalaryEventHandler {
    private final MoneyTransfer moneyTransfer;

    @EventListener
    public void handleEvent(MoneyTransferRequest request) {
        log.info("handling event {}", request);
        moneyTransfer.transfer(request);

    }
}