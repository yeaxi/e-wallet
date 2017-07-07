package ua.dudka.account.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dudka.abstract_test.AbstractRepositoryTest;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.domain.model.vo.Wallets;

import static org.junit.Assert.*;

/**
 * @author Rostislav Dudka
 */
public class AccountRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        accountRepository.deleteAll();
    }

    @Test
    public void accountShouldSave() throws Exception {
        Account account = accountRepository.save(new Account());

        assertNotNull(account);
        assertNotNull(account.getNumber());

        Wallets wallets = account.getWallets();
        assertNotNull(wallets.getByCurrency(Currency.UAH));
        assertNotNull(wallets.getByCurrency(Currency.USD));
        assertNotNull(wallets.getByCurrency(Currency.EUR));
        assertNotNull(wallets.getByCurrency(Currency.BTC));
    }

    @Test
    public void findOneExistentAccountShouldReturnAccount() throws Exception {
        Account expectedAccount = accountRepository.save(new Account());

        Account actualAccount = accountRepository.findOne(expectedAccount.getNumber());

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void deleteExistentAccountShouldDeleteIt() throws Exception {
        Account account = accountRepository.save(new Account());

        accountRepository.delete(account);

        assertNull(accountRepository.findOne(account.getNumber()));

    }
}