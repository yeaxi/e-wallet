package ua.dudka.service;

import org.junit.Before;
import ua.dudka.domain.Account;
import ua.dudka.domain.Currency;
import ua.dudka.repository.AccountRepository;

import java.math.BigDecimal;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.dudka.domain.Currency.UAH;
import static ua.dudka.domain.Currency.USD;

/**
 * @author Rostislav Dudka
 */
public abstract class AbstractCurrencyExchangeTest {
    protected static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(1000);
    protected static final BigDecimal UAH_TO_USD_RATE = BigDecimal.valueOf(0.038);
    protected static final Currency CURRENCY_TO_SELL = UAH, CURRENCY_TO_BUY = USD;


    protected Account testAccount;

    protected AccountRepository accountRepository;

    protected CurrencyExchanger currencyExchanger;

    @Before
    public void setUp() throws Exception {
        testAccount = new Account(INITIAL_BALANCE);

        accountRepository = mock(AccountRepository.class);

        AccountService accountService = mock(AccountService.class);
        when(accountService.getCurrentAccount()).thenReturn(testAccount);

        CurrencyRates ratesService = mock(CurrencyRates.class);
        when(ratesService.getRate(eq(CURRENCY_TO_SELL), eq(CURRENCY_TO_BUY))).thenReturn(UAH_TO_USD_RATE);

        currencyExchanger = new CurrencyExchangerImpl(accountRepository, accountService, ratesService);
    }
}
