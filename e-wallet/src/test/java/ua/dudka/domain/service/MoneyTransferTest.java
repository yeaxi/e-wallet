package ua.dudka.domain.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.dudka.domain.model.Account;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.model.Transaction;
import ua.dudka.domain.model.Wallet;
import ua.dudka.domain.service.exception.AccountNotFoundException;
import ua.dudka.domain.service.exception.NotEnoughBalanceException;
import ua.dudka.domain.service.exception.NotValidRequestException;
import ua.dudka.domain.service.impl.MoneyTransferImpl;
import ua.dudka.repository.AccountRepository;
import ua.dudka.web.dto.MoneyTransferRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.dudka.domain.model.Currency.UAH;

/**
 * @author Rostislav Dudka
 */
public class MoneyTransferTest {

    private static final int SOURCE_ACCOUNT_NUMBER = 15532;
    private static final int DESTINATION_ACCOUNT_NUMBER = 20515;
    private static final BigDecimal DEFAULT_BALANCE = BigDecimal.valueOf(100);
    private static final int NOT_FOUND_ACCOUNT_NUMBER = 404;

    private static Account sourceAccount;
    private static Account destinationAccount;

    private static MoneyTransfer moneyTransfer;
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setUpMocks() throws Exception {
        accountRepository = mock(AccountRepository.class);

        moneyTransfer = new MoneyTransferImpl(accountRepository);
    }

    @Before
    public void setUpAccounts() throws Exception {
        sourceAccount = new Account(DEFAULT_BALANCE);
        destinationAccount = new Account(DEFAULT_BALANCE);
        when(accountRepository.findByNumber(eq(SOURCE_ACCOUNT_NUMBER))).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByNumber(eq(DESTINATION_ACCOUNT_NUMBER))).thenReturn(Optional.of(destinationAccount));
        when(accountRepository.findByNumber(eq(NOT_FOUND_ACCOUNT_NUMBER))).thenReturn(Optional.empty());
    }

    @Test
    public void transferShouldReduceBalanceFromSourceAccount() throws Exception {
        BigDecimal uahWalletAmountBefore = sourceAccount.getWallets().getByCurrency(UAH).getBalance();
        BigDecimal amountToTransfer = BigDecimal.TEN;

        moneyTransfer.transfer(new MoneyTransferRequest(amountToTransfer, UAH, SOURCE_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER));

        BigDecimal uahWalletAmountAfter = sourceAccount.getWallets().getByCurrency(UAH).getBalance();
        assertEquals(uahWalletAmountBefore, uahWalletAmountAfter.add(amountToTransfer));
    }

    @Test
    public void transferShouldIncreaseBalanceInDestinationAccount() throws Exception {
        Account destinationAccount = MoneyTransferTest.destinationAccount;
        BigDecimal uahWalletAmountBefore = destinationAccount.getWallets().getByCurrency(UAH).getBalance();
        BigDecimal amountToTransfer = BigDecimal.TEN;

        moneyTransfer.transfer(new MoneyTransferRequest(amountToTransfer, UAH, SOURCE_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER));

        BigDecimal uahWalletAmountAfter = destinationAccount.getWallets().getByCurrency(UAH).getBalance();
        assertEquals(uahWalletAmountBefore, uahWalletAmountAfter.subtract(amountToTransfer));
    }


    @Test
    public void transferShouldAddTransactionInSourceAccount() throws Exception {
        transferShouldAddTransactionInAccount(sourceAccount);
    }

    @Test
    public void transferShouldAddTransactionInDestinationAccount() throws Exception {
        transferShouldAddTransactionInAccount(destinationAccount);
    }

    private void transferShouldAddTransactionInAccount(Account account) {
        BigDecimal amountToTransfer = BigDecimal.TEN;
        Currency currencyToTransfer = UAH;
        moneyTransfer.transfer(new MoneyTransferRequest(amountToTransfer, currencyToTransfer, SOURCE_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER));

        Wallet uahWallet = account.getWallets().getByCurrency(currencyToTransfer);
        List<Transaction> transactions = uahWallet.getTransactions().getRecent();
        assertFalse(transactions.isEmpty());

        Transaction transaction = transactions.get(0);
        assertEquals(amountToTransfer, transaction.getAmount());
        assertEquals(uahWallet.getBalance(), transaction.getBalance());
    }

    @Test(expected = NotEnoughBalanceException.class)
    public void ifNotEnoughBalanceInSourceAccountTransferShouldThrowNotEnoughBalanceException() throws Exception {
        BigDecimal amountToTransfer = DEFAULT_BALANCE.add(BigDecimal.TEN);

        moneyTransfer.transfer(new MoneyTransferRequest(amountToTransfer, UAH, SOURCE_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER));
    }

    @Test(expected = AccountNotFoundException.class)
    public void ifDestinationAccountNumberIsInvalidTransferShouldThrowAccountNotFoundException() throws Exception {
        moneyTransfer.transfer(new MoneyTransferRequest(BigDecimal.TEN, UAH, SOURCE_ACCOUNT_NUMBER, NOT_FOUND_ACCOUNT_NUMBER));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifMoneyTransferRequestHasEmptyAmountFiledTransferShouldThrowNotValidRequestException() throws Exception {
        moneyTransfer.transfer(new MoneyTransferRequest(null, UAH, SOURCE_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifMoneyTransferRequestHasEmptyCurrencyFiledTransferShouldThrowNotValidRequestException() throws Exception {
        moneyTransfer.transfer(new MoneyTransferRequest(BigDecimal.TEN, null, SOURCE_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifMoneyTransferRequestHasEmptyDestinationFiledTransferShouldThrowNotValidRequestException() throws Exception {
        moneyTransfer.transfer(new MoneyTransferRequest(BigDecimal.TEN, UAH, SOURCE_ACCOUNT_NUMBER, 0));

    }
}