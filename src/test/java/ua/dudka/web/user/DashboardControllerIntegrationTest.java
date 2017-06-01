package ua.dudka.web.user;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.abstract_test.AbstractWebIntegrationTest;
import ua.dudka.domain.Account;
import ua.dudka.domain.Currency;
import ua.dudka.domain.Transaction;
import ua.dudka.domain.Wallet;
import ua.dudka.service.AccountService;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.web.user.DashboardController.Links.DASHBOARD_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = DashboardController.class)
public class DashboardControllerIntegrationTest extends AbstractWebIntegrationTest {

    private static final String TRANSACTION_FORMAT = "<td>%s</td>\n" +
            "                        <td>%s</td>\n" +
            "                        <td>%s</td>\n" +
            "                        <td>%s</td>\n" +
            "                        <td>%s</td>\n" +
            "                        <td>%s</td>";

    @MockBean
    private AccountService accountService;

    private static Account testAccount;

    @BeforeClass
    public static void setUp() throws Exception {
        testAccount = new Account();
        testAccount.applyTransaction(new Transaction(BigDecimal.ONE, Transaction.Type.REFILL, Currency.UAH));

    }

    @Before
    public void setUpMock() throws Exception {

        when(accountService.getCurrentAccount()).thenReturn(testAccount);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void accountWalletsShouldBePresentOnDashboardPage() throws Exception {
        Wallet uahWallet = testAccount.getUahWallet();
        Wallet usdWallet = testAccount.getUsdWallet();
        Wallet eurWallet = testAccount.getEurWallet();
        Wallet btcWallet = testAccount.getBtcWallet();

        mockMvc.perform(get(DASHBOARD_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(content().string(containsString("Account No. " + testAccount.getNumber())))
                .andExpect(content().string(containsString(uahWallet.getBalance().toString() + " " + uahWallet.getCurrency())))
                .andExpect(content().string(containsString(usdWallet.getBalance().toString() + " " + usdWallet.getCurrency())))
                .andExpect(content().string(containsString(eurWallet.getBalance().toString() + " " + eurWallet.getCurrency())))
                .andExpect(content().string(containsString(btcWallet.getBalance().toString() + " " + btcWallet.getCurrency())));
    }


    @Test
    public void accountRecentTransactionsShouldBePresentOnDashboardPage() throws Exception {
        List<Transaction> recentTransactions = testAccount.getRecentTransactions();
        Transaction transaction = recentTransactions.get(0);

        mockMvc.perform(get(DASHBOARD_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(content().string(containsString(
                        String.format(TRANSACTION_FORMAT,
                                transaction.getNumber(),
                                transaction.getAmount(),
                                transaction.getType(),
                                transaction.getCurrency(),
                                transaction.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                transaction.getBalance()))));
    }

}