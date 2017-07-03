package ua.dudka.account.web;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractWebIntegrationTest;
import ua.dudka.account.application.CurrentAccountReader;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.domain.model.Transaction;
import ua.dudka.account.domain.model.Wallet;
import ua.dudka.account.domain.model.vo.MonetaryAmount;
import ua.dudka.account.domain.model.vo.Wallets;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.account.domain.model.Currency.*;
import static ua.dudka.account.web.DashboardController.Links.DASHBOARD_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = DashboardController.class)
public class DashboardControllerIntegrationTest extends AbstractWebIntegrationTest {

    private static final String TRANSACTION_FORMAT = "<td>%s</td>\n" +
            "                        <td>%s</td>\n" +
            "                        <td>%s</td>\n" +
            "                        <td>%s</td>\n" +
            "                        <td>%s</td>";

    @MockBean
    private CurrentAccountReader currentAccountReader;

    private static Account testAccount;

    @BeforeClass
    public static void setUp() throws Exception {
        testAccount = new Account();
        testAccount.refill(MonetaryAmount.of(BigDecimal.ONE, UAH));

    }

    @Before
    public void setUpMock() throws Exception {
        when(currentAccountReader.read()).thenReturn(testAccount);
    }


    @Test
    public void accountWalletsShouldBePresentOnDashboardPage() throws Exception {
        Wallets wallets = testAccount.getWallets();
        Wallet uahWallet = wallets.getByCurrency(UAH);
        Wallet usdWallet = wallets.getByCurrency(USD);
        Wallet eurWallet = wallets.getByCurrency(EUR);
        Wallet btcWallet = wallets.getByCurrency(BTC);

        mockMvc.perform(get(DASHBOARD_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(content().string(containsString("Account No. " + testAccount.getNumber())))
                .andExpect(content().string(containsString(uahWallet.getBalance() + " " + uahWallet.getCurrency())))
                .andExpect(content().string(containsString(usdWallet.getBalance() + " " + usdWallet.getCurrency())))
                .andExpect(content().string(containsString(eurWallet.getBalance() + " " + eurWallet.getCurrency())))
                .andExpect(content().string(containsString(btcWallet.getBalance() + " " + btcWallet.getCurrency())));
    }


    @Test
    public void accountRecentTransactionsShouldBePresentOnDashboardPage() throws Exception {
        List<Transaction> recentTransactions = testAccount.getWallets().getByCurrency(UAH).getTransactions().getRecent();
        Transaction transaction = recentTransactions.get(0);

        mockMvc.perform(get(DASHBOARD_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(content().string(containsString(
                        String.format(TRANSACTION_FORMAT,
                                transaction.getNumber(),
                                transaction.getAmount(),
                                transaction.getType(),
                                transaction.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                transaction.getBalance()))));
    }

}