package ua.dudka.service;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.EWalletApplication;
import ua.dudka.domain.Account;
import ua.dudka.repository.AccountRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class AccountServiceTest {

    private static final Integer DEV_ACCOUNT_ID = EWalletApplication.DevConfig.getDevAccountNumber();
    private AccountRepository accountRepository;
    private Account testAccount;


    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        accountRepository = mock(AccountRepository.class);

        testAccount = new Account();
        when(accountRepository.findOne(eq(DEV_ACCOUNT_ID))).thenReturn(testAccount);

        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void getCurrentAccountShouldRetrieveAccountFromRepository() throws Exception {
        Account currentAccount = accountService.getCurrentAccount();

        assertEquals(testAccount, currentAccount);

        verify(accountRepository).findOne(eq(DEV_ACCOUNT_ID));
    }

}