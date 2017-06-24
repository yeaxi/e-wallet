package ua.dudka.hrm.employee.service;

import org.junit.Before;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.application.CurrentAccountReader;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.repository.AccountRepository;
import ua.dudka.account.domain.service.CurrencyExchanger;
import ua.dudka.account.domain.service.impl.CurrencyExchangerImpl;
import ua.dudka.account.domain.service.CurrencyRates;

import java.math.BigDecimal;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.dudka.account.domain.model.Currency.UAH;
import static ua.dudka.account.domain.model.Currency.USD;

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

        CurrentAccountReader currentAccountReader = mock(CurrentAccountReader.class);
        when(currentAccountReader.read()).thenReturn(testAccount);

        CurrencyRates ratesService = mock(CurrencyRates.class);
        when(ratesService.getRate(eq(CURRENCY_TO_SELL), eq(CURRENCY_TO_BUY))).thenReturn(UAH_TO_USD_RATE);

        currencyExchanger = new CurrencyExchangerImpl(accountRepository, currentAccountReader, ratesService);
    }
}
