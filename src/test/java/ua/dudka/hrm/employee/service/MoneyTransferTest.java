package ua.dudka.hrm.employee.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.application.CurrentAccountReader;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.domain.model.Transaction;
import ua.dudka.account.domain.service.exception.AccountNotFoundException;
import ua.dudka.account.domain.service.exception.NotEnoughBalanceException;
import ua.dudka.account.domain.service.exception.NotValidRequestException;
import ua.dudka.account.repository.AccountRepository;
import ua.dudka.account.domain.service.MoneyTransfer;
import ua.dudka.account.domain.service.impl.MoneyTransferImpl;
import ua.dudka.account.web.dto.MoneyTransferRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.dudka.account.domain.model.Currency.UAH;

/**
 * @author Rostislav Dudka
 */
public class MoneyTransferTest {

    private static final int DESTINATION_ACCOUNT_NUMBER = 20515;
    private static final BigDecimal DEFAULT_BALANCE = BigDecimal.valueOf(100);
    private static final int NOT_FOUND_DESTINATION_ACCOUNT_NUMBER = 404;

    private static Account currentAccount;
    private static Account destinationAccount;

    private static CurrentAccountReader currentAccountReader;
    private static MoneyTransfer moneyTransfer;
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setUpMocks() throws Exception {
        currentAccountReader = mock(CurrentAccountReader.class);
        accountRepository = mock(AccountRepository.class);
        moneyTransfer = new MoneyTransferImpl(accountRepository, currentAccountReader);
    }

    @Before
    public void setUpAccounts() throws Exception {
        currentAccount = new Account(DEFAULT_BALANCE);
        destinationAccount = new Account(DEFAULT_BALANCE);
        when(currentAccountReader.read()).thenReturn(currentAccount);
        when(accountRepository.findByNumber(eq(DESTINATION_ACCOUNT_NUMBER))).thenReturn(Optional.of(destinationAccount));
        when(accountRepository.findByNumber(eq(NOT_FOUND_DESTINATION_ACCOUNT_NUMBER))).thenReturn(Optional.empty());
    }

    @Test
    public void transferShouldReduceBalanceFromSourceAccount() throws Exception {
        BigDecimal uahWalletAmountBefore = currentAccount.getUahWallet().getBalance();
        BigDecimal amountToTransfer = BigDecimal.TEN;

        moneyTransfer.transfer(new MoneyTransferRequest(amountToTransfer, UAH, DESTINATION_ACCOUNT_NUMBER));

        BigDecimal uahWalletAmountAfter = currentAccount.getUahWallet().getBalance();
        assertEquals(uahWalletAmountBefore, uahWalletAmountAfter.add(amountToTransfer));
    }

    @Test
    public void transferShouldIncreaseBalanceInDestinationAccount() throws Exception {
        Account destinationAccount = MoneyTransferTest.destinationAccount;
        BigDecimal uahWalletAmountBefore = destinationAccount.getUahWallet().getBalance();
        BigDecimal amountToTransfer = BigDecimal.TEN;

        moneyTransfer.transfer(new MoneyTransferRequest(amountToTransfer, UAH, DESTINATION_ACCOUNT_NUMBER));

        BigDecimal uahWalletAmountAfter = destinationAccount.getUahWallet().getBalance();
        assertEquals(uahWalletAmountBefore, uahWalletAmountAfter.subtract(amountToTransfer));
    }


    @Test
    public void transferShouldAddTransactionInSourceAccount() throws Exception {
        transferShouldAddTransactionInAccount(currentAccount);
    }

    @Test
    public void transferShouldAddTransactionInDestinationAccount() throws Exception {
        transferShouldAddTransactionInAccount(destinationAccount);
    }

    private void transferShouldAddTransactionInAccount(Account account) {
        BigDecimal amountToTransfer = BigDecimal.TEN;
        Currency currencyToTransfer = UAH;
        moneyTransfer.transfer(new MoneyTransferRequest(amountToTransfer, currencyToTransfer, DESTINATION_ACCOUNT_NUMBER));

        List<Transaction> transactions = account.getRecentTransactions();
        assertFalse(transactions.isEmpty());

        Transaction transaction = transactions.get(0);
        assertEquals(amountToTransfer, transaction.getAmount());
        assertEquals(currencyToTransfer, transaction.getCurrency());
        assertEquals(account.getUahWallet().getBalance(), transaction.getBalance());
    }

    @Test(expected = NotEnoughBalanceException.class)
    public void ifNotEnoughBalanceInSourceAccountTransferShouldThrowNotEnoughBalanceException() throws Exception {
        BigDecimal amountToTransfer = DEFAULT_BALANCE.add(BigDecimal.TEN);

        moneyTransfer.transfer(new MoneyTransferRequest(amountToTransfer, UAH, DESTINATION_ACCOUNT_NUMBER));
    }

    @Test(expected = AccountNotFoundException.class)
    public void ifDestinationAccountNumberIsInvalidTransferShouldThrowAccountNotFoundException() throws Exception {
        moneyTransfer.transfer(new MoneyTransferRequest(BigDecimal.TEN, UAH, NOT_FOUND_DESTINATION_ACCOUNT_NUMBER));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifMoneyTransferRequestHasEmptyAmountFiledTransferShouldThrowNotValidRequestException() throws Exception {
        moneyTransfer.transfer(new MoneyTransferRequest(null, UAH, DESTINATION_ACCOUNT_NUMBER));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifMoneyTransferRequestHasEmptyCurrencyFiledTransferShouldThrowNotValidRequestException() throws Exception {
        moneyTransfer.transfer(new MoneyTransferRequest(BigDecimal.TEN, null, DESTINATION_ACCOUNT_NUMBER));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifMoneyTransferRequestHasEmptyDestinationFiledTransferShouldThrowNotValidRequestException() throws Exception {
        moneyTransfer.transfer(new MoneyTransferRequest(BigDecimal.TEN, UAH, 0));

    }
}