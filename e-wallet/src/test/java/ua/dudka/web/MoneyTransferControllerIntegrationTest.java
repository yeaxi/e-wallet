package ua.dudka.web;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractWebIntegrationTest;
import ua.dudka.application.CurrentAccountReader;
import ua.dudka.domain.model.Account;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.service.MoneyTransfer;
import ua.dudka.web.dto.MoneyTransferRequest;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.web.MoneyTransferController.Links.MONEY_TRANSFER_PAGE_URL;
import static ua.dudka.web.MoneyTransferController.Links.TRANSFER_MONEY_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = MoneyTransferController.class, secure = false)
public class MoneyTransferControllerIntegrationTest extends AbstractWebIntegrationTest {

    @MockBean
    private CurrentAccountReader currentAccountReader;

    @MockBean
    private MoneyTransfer moneyTransfer;

    private static Account testAccount;

    @BeforeClass
    public static void setUp() throws Exception {
        testAccount = new Account();
    }

    @Before
    public void setUpMock() throws Exception {
        when(currentAccountReader.read()).thenReturn(testAccount);
    }

    @Test
    public void accountNumberShouldBePresentOnMoneyTransferPage() throws Exception {
        mockMvc.perform(get(MONEY_TRANSFER_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(content().string(containsString("Account No. " + testAccount.getNumber())));
    }

    @Test
    public void transferMoneyRequestShouldInvokeProcessingInServiceAndRefreshPage() throws Exception {
        String amount = 10 + "";
        Currency currency = Currency.UAH;
        int destinationAccountNumber = 205;

        mockMvc.perform(post(TRANSFER_MONEY_URL)
                .param("amount", amount)
                .param("currency", currency.toString())
                .param("destinationAccountNumber", destinationAccountNumber + "")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());

        verify(moneyTransfer).transfer(eq(new MoneyTransferRequest(new BigDecimal(amount), currency,
                testAccount.getNumber(), destinationAccountNumber)));
    }
}