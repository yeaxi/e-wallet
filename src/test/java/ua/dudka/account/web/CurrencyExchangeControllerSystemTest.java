package ua.dudka.account.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractSystemTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.account.web.CurrencyExchangeController.Links.CURRENCY_EXCHANGE_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
public class CurrencyExchangeControllerSystemTest extends AbstractSystemTest {

    @Test
    public void getCurrencyExchangePageShouldReturnOK() throws Exception {
        mockMvc.perform(get(CURRENCY_EXCHANGE_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }
}
