package ua.dudka.service;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.EWalletApplication;
import ua.dudka.domain.Account;
import ua.dudka.repository.AccountRepository;
import ua.dudka.service.impl.CurrentAccountReaderImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class CurrentAccountReaderTest {

    private static final Integer DEV_ACCOUNT_ID = EWalletApplication.DevConfig.getDevAccountNumber();
    private AccountRepository accountRepository;
    private Account testAccount;


    private CurrentAccountReader currentAccountReader;

    @Before
    public void setUp() throws Exception {
        accountRepository = mock(AccountRepository.class);

        testAccount = new Account();
        when(accountRepository.findOne(eq(DEV_ACCOUNT_ID))).thenReturn(testAccount);

        currentAccountReader = new CurrentAccountReaderImpl(accountRepository);
    }

    @Test
    public void getCurrentAccountShouldRetrieveAccountFromRepository() throws Exception {
        Account currentAccount = currentAccountReader.read();

        assertEquals(testAccount, currentAccount);

        verify(accountRepository).findOne(eq(DEV_ACCOUNT_ID));
    }

}