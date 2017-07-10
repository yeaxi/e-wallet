package ua.dudka.domain.model;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.domain.model.vo.Transactions;
import ua.dudka.domain.service.exception.NotEnoughBalanceException;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static ua.dudka.domain.model.Currency.USD;
import static ua.dudka.domain.model.Transaction.Type;

/**
 * @author Rostislav Dudka
 */
public class WalletTest {

    private static final Currency WALLET_CURRENCY = USD;
    private Wallet wallet;

    @Before
    public void setUp() throws Exception {
        wallet = new Wallet(BigDecimal.TEN, WALLET_CURRENCY);
    }

    @Test
    public void refillShouldIncreaseBalance() throws Exception {
        BigDecimal balanceBefore = wallet.getBalance();
        BigDecimal amountToRefill = BigDecimal.TEN;

        wallet.refill(amountToRefill);

        BigDecimal expectedBalance = balanceBefore.add(amountToRefill);
        assertEquals(expectedBalance, wallet.getBalance());
    }

    @Test
    public void refillShouldAddRefillTransaction() throws Exception {
        BigDecimal amountToRefill = BigDecimal.TEN;

        wallet.refill(amountToRefill);

        Transactions transactions = wallet.getTransactions();
        Transaction refillTransaction = transactions.get(0);

        Transaction expectedTransaction = new Transaction(amountToRefill, Type.REFILL, wallet.getBalance());
        assertEquals(expectedTransaction, refillTransaction);
    }

    @Test
    public void withdrawShouldReduceBalance() throws Exception {
        BigDecimal balanceBefore = wallet.getBalance();
        BigDecimal amountToWithdraw = BigDecimal.TEN;

        wallet.withdraw(amountToWithdraw);

        BigDecimal expectedBalance = balanceBefore.subtract(amountToWithdraw);
        assertEquals(expectedBalance, wallet.getBalance());
    }

    @Test
    public void withdrawShouldAddWithdrawTransaction() throws Exception {
        BigDecimal amountToWithdraw = BigDecimal.TEN;

        wallet.withdraw(amountToWithdraw);

        Transactions transactions = wallet.getTransactions();
        Transaction withdrawTransaction = transactions.get(0);

        Transaction expectedTransaction = new Transaction(amountToWithdraw, Type.WITHDRAWAL, wallet.getBalance());
        assertEquals(expectedTransaction, withdrawTransaction);
    }

    @Test(expected = NotEnoughBalanceException.class)
    public void ifBalanceIsNonEnoughWithdrawShouldThrowException() throws Exception {
        BigDecimal balanceBefore = wallet.getBalance();
        BigDecimal amountToWithdraw = balanceBefore.add(BigDecimal.TEN);

        wallet.withdraw(amountToWithdraw);
    }
}