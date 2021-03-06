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
import ua.dudka.domain.service.CurrencyExchanger;
import ua.dudka.domain.service.CurrencyExchanger.ExchangeType;
import ua.dudka.web.dto.CurrencyExchangeRequest;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.domain.service.CurrencyExchanger.ExchangeType.BUY;
import static ua.dudka.domain.service.CurrencyExchanger.ExchangeType.SELL;
import static ua.dudka.web.CurrencyExchangeController.Links.CURRENCY_EXCHANGE_PAGE_URL;
import static ua.dudka.web.CurrencyExchangeController.Links.CURRENCY_EXCHANGE_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = CurrencyExchangeController.class,secure = false)
public class CurrencyExchangeControllerIntegrationTest extends AbstractWebIntegrationTest {

    @MockBean
    private CurrentAccountReader currentAccountReader;

    @MockBean
    private CurrencyExchanger currencyExchanger;

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
    public void accountNumberShouldBePresentOnCurrencyExchangePage() throws Exception {
        mockMvc.perform(get(CURRENCY_EXCHANGE_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(content().string(containsString("Account No. " + testAccount.getNumber())));
    }

    @Test
    public void sellCurrencyRequestShouldInvokeProcessingInServiceAndRefreshPage() throws Exception {
        BigDecimal amount = BigDecimal.TEN;
        Currency sellCurrency = Currency.UAH;
        Currency buyCurrency = Currency.USD;
        ExchangeType exchangeType = SELL;

        mockMvc.perform(post(CURRENCY_EXCHANGE_URL)
                .param("amount", amount.toString())
                .param("sellCurrency", sellCurrency.toString())
                .param("buyCurrency", buyCurrency.toString())
                .param("exchangeType", exchangeType.toString())

                .accept(MediaType.ALL))
                .andExpect(status().isOk());

        verify(currencyExchanger).exchange(eq(new CurrencyExchangeRequest(amount, sellCurrency, buyCurrency, exchangeType)));

    }

    @Test
    public void buyCurrencyRequestShouldInvokeProcessingInServiceAndRefreshPage() throws Exception {
        BigDecimal amount = BigDecimal.TEN;
        Currency sellCurrency = Currency.UAH;
        Currency buyCurrency = Currency.USD;
        ExchangeType exchangeType = BUY;

        mockMvc.perform(post(CURRENCY_EXCHANGE_URL)
                .param("amount", amount.toString())
                .param("buyCurrency", buyCurrency.toString())
                .param("sellCurrency", sellCurrency.toString())
                .param("exchangeType", exchangeType.toString())
                .accept(MediaType.ALL))
                .andExpect(status().isOk());

        verify(currencyExchanger).exchange(eq(new CurrencyExchangeRequest(amount, sellCurrency, buyCurrency, exchangeType)));

    }
}