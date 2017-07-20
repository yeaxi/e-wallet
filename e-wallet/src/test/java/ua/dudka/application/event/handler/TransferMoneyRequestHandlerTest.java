package ua.dudka.application.event.handler;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.application.event.handler.TransferMoneyRequestHandler;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.service.MoneyTransfer;
import ua.dudka.web.dto.MoneyTransferRequest;

import java.math.BigDecimal;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Rostislav Dudka
 */
public class TransferMoneyRequestHandlerTest {

    private TransferMoneyRequestHandler handler;
    private MoneyTransfer moneyTransfer;

    @Before
    public void setUp() throws Exception {
        moneyTransfer = mock(MoneyTransfer.class);
        handler = new TransferMoneyRequestHandler(moneyTransfer);
    }

    @Test
    public void handleRequestShouldCallMoneyTransfer() throws Exception {
        int fromNumber = 1, toNumber = 1;
        MoneyTransferRequest request = new MoneyTransferRequest(BigDecimal.TEN, Currency.USD, fromNumber, toNumber);

        handler.handle(request);

        verify(moneyTransfer).transfer(eq(request));
    }
}