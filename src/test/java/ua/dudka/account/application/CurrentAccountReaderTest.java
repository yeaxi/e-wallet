package ua.dudka.account.application;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.dudka.account.application.config.AccountConfig.DEV_ACCOUNT_EMAIL;
import static ua.dudka.account.application.config.AccountConfig.DEV_ACCOUNT_ID;

/**
 * @author Rostislav Dudka
 */
public class CurrentAccountReaderTest {

    private Account testAccount;

    private CurrentAccountReader currentAccountReader;

    @Before
    public void setUp() throws Exception {
        AccountRepository accountRepository = mock(AccountRepository.class);

        testAccount = new Account(DEV_ACCOUNT_ID, DEV_ACCOUNT_EMAIL, BigDecimal.valueOf(10_000));
        when(accountRepository.findByEmail(eq(testAccount.getEmail()))).thenReturn(Optional.of(testAccount));

        currentAccountReader = new CurrentAccountReaderImpl(accountRepository);
    }

    @Test
    public void getCurrentAccountShouldRetrieveAccountFromRepository() throws Exception {
        Account currentAccount = currentAccountReader.read();

        assertEquals(testAccount, currentAccount);
    }

}