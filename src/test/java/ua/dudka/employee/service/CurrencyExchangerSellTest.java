package ua.dudka.employee.service;

import org.junit.Test;
import ua.dudka.employee.domain.Transaction;
import ua.dudka.employee.domain.Transaction.Type;
import ua.dudka.employee.exception.NotEnoughBalanceException;
import ua.dudka.employee.exception.NotValidRequestException;
import ua.dudka.employee.web.dto.CurrencyExchangeRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static ua.dudka.employee.service.CurrencyExchanger.ExchangeType.SELL;

/**
 * @author Rostislav Dudka
 */
public class CurrencyExchangerSellTest extends AbstractCurrencyExchangeTest {

    private static final BigDecimal AMOUNT_TO_SELL = BigDecimal.TEN;
    private static final CurrencyExchangeRequest CURRENCY_SELL_REQUEST = new CurrencyExchangeRequest(AMOUNT_TO_SELL, CURRENCY_TO_SELL, CURRENCY_TO_BUY, SELL);


    @Test
    public void sellCurrencyShouldReduceSoldCurrencyAmount() throws Exception {
        final BigDecimal uahWalletBalanceBefore = testAccount.getUahWallet().getBalance();

        currencyExchanger.exchange(CURRENCY_SELL_REQUEST);

        BigDecimal uahWalletBalanceAfter = testAccount.getUahWallet().getBalance();

        assertBalanceEquals(uahWalletBalanceBefore, uahWalletBalanceAfter.add(AMOUNT_TO_SELL));
    }

    @Test
    public void sellCurrencyShouldIncreaseBoughtCurrencyAmount() throws Exception {
        final BigDecimal usdWalletBalanceBefore = testAccount.getUsdWallet().getBalance();

        currencyExchanger.exchange(CURRENCY_SELL_REQUEST);

        BigDecimal usdWalletBalanceAfter = testAccount.getUsdWallet().getBalance();
        BigDecimal boughtAmount = AMOUNT_TO_SELL.multiply(UAH_TO_USD_RATE);

        assertBalanceEquals(usdWalletBalanceBefore, usdWalletBalanceAfter.subtract(boughtAmount));
    }

    private void assertBalanceEquals(BigDecimal first, BigDecimal second) {
        //see @BigDecimal equals javadoc
        assertTrue(first.compareTo(second) == 0);
    }

    @Test
    public void sellCurrencyShouldAddWithdrawTransaction() throws Exception {
        currencyExchanger.exchange(CURRENCY_SELL_REQUEST);

        List<Transaction> transactions = testAccount.getRecentTransactions();
        assertFalse(transactions.isEmpty());

        Transaction expectedTransaction = new Transaction(AMOUNT_TO_SELL, Type.WITHDRAWAL,
                CURRENCY_TO_SELL, testAccount.getUahWallet().getBalance());

        Transaction actualTransaction = transactions.get(1);

        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void sellCurrencyShouldAddRefillTransaction() throws Exception {
        currencyExchanger.exchange(CURRENCY_SELL_REQUEST);

        List<Transaction> transactions = testAccount.getRecentTransactions();
        assertFalse(transactions.isEmpty());

        BigDecimal boughtAmount = AMOUNT_TO_SELL.multiply(UAH_TO_USD_RATE);
        Transaction expectedTransaction = new Transaction(boughtAmount, Type.REFILL, CURRENCY_TO_BUY, testAccount.getUsdWallet().getBalance());
        Transaction actualTransaction = transactions.get(0);

        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void sellCurrencyShouldSaveAccountToRepository() throws Exception {
        currencyExchanger.exchange(CURRENCY_SELL_REQUEST);

        verify(accountRepository).save(eq(testAccount));
    }

    @Test(expected = NotEnoughBalanceException.class)
    public void ifNotEnoughSoldCurrencyBalanceSellCurrencyShouldThrowException() throws Exception {
        BigDecimal amountToSell = INITIAL_BALANCE.add(AMOUNT_TO_SELL);

        currencyExchanger.exchange(new CurrencyExchangeRequest(amountToSell, CURRENCY_TO_SELL, CURRENCY_TO_BUY, SELL));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifSellCurrencyRequestHasEmptyAmountSellShouldThrowException() throws Exception {
        currencyExchanger.exchange(new CurrencyExchangeRequest(null, CURRENCY_TO_SELL, CURRENCY_TO_BUY, SELL));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifSellCurrencyRequestHasEmptySellCurrencySellShouldThrowException() throws Exception {
        currencyExchanger.exchange(new CurrencyExchangeRequest(AMOUNT_TO_SELL, null, CURRENCY_TO_BUY, SELL));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifSellCurrencyRequestHasEmptyBuyCurrencySellShouldThrowException() throws Exception {
        currencyExchanger.exchange(new CurrencyExchangeRequest(AMOUNT_TO_SELL, CURRENCY_TO_SELL, null, SELL));
    }

    @Test(expected = NotValidRequestException.class)
    public void ifSellCurrencyRequestHasEmptyTypeSellShouldThrowException() throws Exception {
        currencyExchanger.exchange(new CurrencyExchangeRequest(AMOUNT_TO_SELL, CURRENCY_TO_SELL, CURRENCY_TO_SELL, null));
    }

}