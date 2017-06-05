package ua.dudka.admin.domain;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.employee.domain.*;
import ua.dudka.employee.domain.Transaction.Type;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Rostislav Dudka
 */
public class AdminTest {

    private static final BigDecimal ADMIN_BALANCE = BigDecimal.valueOf(2000);

    private Admin testAdmin;
    private Employee testEmployee;

    @Before
    public void setUp() throws Exception {
        testAdmin = new Admin(ADMIN_BALANCE);
        testEmployee = Employee.builder()
                .salary(Salary.of(BigDecimal.valueOf(100), Currency.USD))
                .build();
    }

    @Test
    public void paySalaryShouldWithdrawSalaryAmountFromAdminAccount() throws Exception {
        testAdmin.paySalary(testEmployee);

        Salary salary = testEmployee.getSalary();
        BigDecimal balanceAfter = testAdmin.getWalletByCurrency(salary.getCurrency()).getBalance();

        BigDecimal amount = salary.getAmount();

        assertEquals(ADMIN_BALANCE.subtract(amount), balanceAfter);

    }

    @Test
    public void paySalaryShouldIncreaseEmployeeAccountAmount() throws Exception {
        Salary salary = testEmployee.getSalary();
        Wallet wallet = testEmployee.getAccount().getWalletByCurrency(salary.getCurrency());
        BigDecimal employeeBalanceBefore = wallet.getBalance();

        testAdmin.paySalary(testEmployee);

        BigDecimal employeeBalanceAfter = wallet.getBalance();
        BigDecimal salaryAmount = BigDecimal.valueOf(salary.getAmount().longValue());

        assertEquals(employeeBalanceBefore.add(salaryAmount), employeeBalanceAfter);
    }


    @Test
    public void paySalaryShouldAddRefillTransactionToEmployeeAccount() throws Exception {
        testAdmin.paySalary(testEmployee);

        Transaction refillTransaction = testEmployee.getAccount().getRecentTransactions().get(0);

        assertEquals(testEmployee.getSalary().getAmount(), refillTransaction.getAmount());
        assertEquals(testEmployee.getSalary().getCurrency(), refillTransaction.getCurrency());
        assertEquals(Type.REFILL, refillTransaction.getType());
    }

    @Test
    public void paySalaryShouldAddDebtToAdminIfNotEnoughAmountToWithdraw() throws Exception {
        testEmployee.changeSalary(Salary.of(ADMIN_BALANCE.add(BigDecimal.TEN), Currency.USD));


        testAdmin.paySalary(testEmployee);

        List<Debt> debts = testAdmin.getDebts();

        assertFalse(debts.isEmpty());

        Debt debt = debts.get(0);
        assertEquals(debt.getEmployeeId(), testEmployee.getId());
        assertEquals(debt.getSalary(), testEmployee.getSalary());
    }
}