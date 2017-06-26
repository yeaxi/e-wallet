package ua.dudka.hrm.domain.model;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.account.domain.model.Transaction;
import ua.dudka.account.domain.model.Transaction.Type;
import ua.dudka.account.domain.model.Wallet;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.domain.model.company.Debt;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.domain.model.employee.Salary;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Rostislav Dudka
 */
public class CompanyTest {

    private static final BigDecimal ADMIN_BALANCE = BigDecimal.valueOf(2000);

    private Company testCompany;
    private Employee testEmployee;

    @Before
    public void setUp() throws Exception {
        testCompany = new Company(ADMIN_BALANCE);
        testEmployee = Employee.builder()
                .salary(Salary.of(BigDecimal.valueOf(100), Currency.USD))
                .build();
    }

    @Test
    public void paySalaryShouldWithdrawSalaryAmountFromAdminAccount() throws Exception {
        testCompany.paySalary(testEmployee);

        Salary salary = testEmployee.getSalary();
        BigDecimal balanceAfter = testCompany.getWalletByCurrency(salary.getCurrency()).getBalance();

        BigDecimal amount = salary.getAmount();

        assertEquals(ADMIN_BALANCE.subtract(amount), balanceAfter);

    }

    @Test
    public void paySalaryShouldIncreaseEmployeeAccountAmount() throws Exception {
        Salary salary = testEmployee.getSalary();
        Wallet wallet = testEmployee.getAccount().getWalletByCurrency(salary.getCurrency());
        BigDecimal employeeBalanceBefore = wallet.getBalance();

        testCompany.paySalary(testEmployee);

        BigDecimal employeeBalanceAfter = wallet.getBalance();
        BigDecimal salaryAmount = BigDecimal.valueOf(salary.getAmount().longValue());

        assertEquals(employeeBalanceBefore.add(salaryAmount), employeeBalanceAfter);
    }


    @Test
    public void paySalaryShouldAddRefillTransactionToEmployeeAccount() throws Exception {
        testCompany.paySalary(testEmployee);

        Transaction refillTransaction = testEmployee.getAccount().getRecentTransactions().get(0);

        assertEquals(testEmployee.getSalary().getAmount(), refillTransaction.getAmount());
        assertEquals(testEmployee.getSalary().getCurrency(), refillTransaction.getCurrency());
        assertEquals(Type.REFILL, refillTransaction.getType());
    }

    @Test
    public void paySalaryShouldAddDebtToAdminIfNotEnoughAmountToWithdraw() throws Exception {
        testEmployee.changeSalary(Salary.of(ADMIN_BALANCE.add(BigDecimal.TEN), Currency.USD));


        testCompany.paySalary(testEmployee);

        List<Debt> debts = testCompany.getDebts();

        assertFalse(debts.isEmpty());

        Debt debt = debts.get(0);
        assertEquals(debt.getEmployeeId(), testEmployee.getId());
        assertEquals(debt.getSalary(), testEmployee.getSalary());
    }
}