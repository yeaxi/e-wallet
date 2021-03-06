package ua.dudka.domain.service;

import org.junit.Test;
import ua.dudka.domain.model.Transaction;
import ua.dudka.domain.model.Transaction.Type;
import ua.dudka.domain.model.Wallet;
import ua.dudka.domain.service.exception.NotEnoughBalanceException;
import ua.dudka.domain.service.exception.NotValidRequestException;
import ua.dudka.web.dto.CurrencyExchangeRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static ua.dudka.domain.service.CurrencyExchanger.ExchangeType.BUY;

/**
 * @author Rostislav Dudka
 */
public class CurrencyExchangerBuyTest extends AbstractCurrencyExchangeTest {

    private static final BigDecimal AMOUNT_TO_BUY = BigDecimal.TEN;
    private static final CurrencyExchangeRequest CURRENCY_BUY_REQUEST = new CurrencyExchangeRequest(AMOUNT_TO_BUY, CURRENCY_TO_SELL, CURRENCY_TO_BUY, BUY);

    @Test
    public void buyShouldIncreaseBoughtCurrencyAmount() throws Exception {
        final BigDecimal usdWalletBalanceBefore = testAccount.getWallets().getByCurrency(CURRENCY_TO_BUY).getBalance();

        currencyExchanger.exchange(CURRENCY_BUY_REQUEST);

        BigDecimal usdWalletBalanceAfter = testAccount.getWallets().getByCurrency(CURRENCY_TO_BUY).getBalance();

        assertBalanceEquals(usdWalletBalanceBefore, usdWalletBalanceAfter.subtract(AMOUNT_TO_BUY));
    }

    @Test
    public void buyShouldReduceSoldCurrencyAmount() throws Exception {
        final BigDecimal uahWalletBalanceBefore = testAccount.getWallets().getByCurrency(CURRENCY_TO_SELL).getBalance();

        currencyExchanger.exchange(CURRENCY_BUY_REQUEST);

        BigDecimal uahWalletBalanceAfter = testAccount.getWallets().getByCurrency(CURRENCY_TO_SELL).getBalance();

        BigDecimal soldAmount = AMOUNT_TO_BUY.divide(UAH_TO_USD_RATE, 2, BigDecimal.ROUND_UP);
        assertBalanceEquals(uahWalletBalanceBefore, uahWalletBalanceAfter.add(soldAmount));
    }

    private void assertBalanceEquals(BigDecimal usdWalletBalanceBefore, BigDecimal subtract) {
        //see @BigDecimal javadoc
        assertTrue(usdWalletBalanceBefore.compareTo(subtract) == 0);
    }

    @Test
    public void buyShouldAddWithdrawTransaction() throws Exception {
        currencyExchanger.exchange(CURRENCY_BUY_REQUEST);

        Wallet sellCurrencyWallet = testAccount.getWallets().getByCurrency(CURRENCY_TO_SELL);
        List<Transaction> transactions = sellCurrencyWallet.getTransactions();
        assertFalse(transactions.isEmpty());

        BigDecimal soldAmount = AMOUNT_TO_BUY.divide(UAH_TO_USD_RATE, 2, BigDecimal.ROUND_UP);
        Transaction expectedTransaction = new Transaction(soldAmount, Type.WITHDRAWAL, sellCurrencyWallet.getBalance());

        Transaction actualTransaction = transactions.get(0);

        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void buyShouldAddRefillTransaction() throws Exception {
        currencyExchanger.exchange(CURRENCY_BUY_REQUEST);

        Wallet wallet = testAccount.getWallets().getByCurrency(CURRENCY_TO_BUY);
        List<Transaction> transactions = wallet.getTransactions();
        assertFalse(transactions.isEmpty());

        Transaction expectedTransaction = new Transaction(AMOUNT_TO_BUY, Type.REFILL, wallet.getBalance());
        Transaction actualTransaction = transactions.get(0);

        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void buyShouldSaveAccountToRepository() throws Exception {
        currencyExchanger.exchange(CURRENCY_BUY_REQUEST);

        verify(accountRepository).save(eq(testAccount));
    }

    @Test(expected = NotEnoughBalanceException.class)
    public void ifNotEnoughSoldCurrencyBalanceBuyShouldThrowException() throws Exception {
        BigDecimal amountToBuy = INITIAL_BALANCE.add(AMOUNT_TO_BUY);

        currencyExchanger.exchange(new CurrencyExchangeRequest(amountToBuy, CURRENCY_TO_SELL, CURRENCY_TO_BUY, BUY));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifCurrencyExchangeRequestHasEmptyAmountBuyShouldThrowException() throws Exception {
        currencyExchanger.exchange(new CurrencyExchangeRequest(null, CURRENCY_TO_SELL, CURRENCY_TO_BUY, BUY));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifCurrencyExchangeRequestHasEmptyBuyCurrencyBuyShouldThrowException() throws Exception {
        currencyExchanger.exchange(new CurrencyExchangeRequest(AMOUNT_TO_BUY, null, CURRENCY_TO_BUY, BUY));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifCurrencyExchangeRequestHasEmptySellCurrencyBuyShouldThrowException() throws Exception {
        currencyExchanger.exchange(new CurrencyExchangeRequest(AMOUNT_TO_BUY, CURRENCY_TO_SELL, null, BUY));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifCurrencyExchangeRequestHasEmptyTypeBuyShouldThrowException() throws Exception {
        currencyExchanger.exchange(new CurrencyExchangeRequest(AMOUNT_TO_BUY, CURRENCY_TO_SELL, CURRENCY_TO_BUY, null));
    }

}