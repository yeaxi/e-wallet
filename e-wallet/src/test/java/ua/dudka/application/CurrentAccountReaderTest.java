package ua.dudka.application;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.EWalletApplication;
import ua.dudka.application.config.AccountConfig;
import ua.dudka.domain.model.Account;
import ua.dudka.repository.AccountRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Rostislav Dudka
 */
public class CurrentAccountReaderTest {

    private Account testAccount;

    private CurrentAccountReader currentAccountReader;

    @Before
    public void setUp() throws Exception {
        AccountRepository accountRepository = mock(AccountRepository.class);

        testAccount = new Account(1, "mail", "password");
        when(accountRepository.findByEmail(eq(AccountConfig.DEV_ACCOUNT_EMAIL))).thenReturn(Optional.of(testAccount));

        currentAccountReader = new CurrentAccountReaderImpl(accountRepository);
    }

    @Test
    public void getCurrentAccountShouldRetrieveAccountFromRepository() throws Exception {
        Account currentAccount = currentAccountReader.read();

        assertEquals(testAccount, currentAccount);
    }

}