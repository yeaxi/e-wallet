package ua.dudka.account.application.event.handler;

import org.junit.Test;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.domain.service.MoneyTransfer;
import ua.dudka.account.web.dto.MoneyTransferRequest;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;

/**
 * @author Rostislav Dudka
 */
public class PaySalaryEventHandlerTest {

    @Test
    public void handleEventShouldCallTransferMoneyService() throws Exception {
        MoneyTransfer mock = mock(MoneyTransfer.class);
        PaySalaryEventHandler handler = new PaySalaryEventHandler(mock);

        handler.handleEvent(buildRequest());

    }

    private MoneyTransferRequest buildRequest() {
        return new MoneyTransferRequest(BigDecimal.TEN, Currency.USD, 1, 1);
    }

}