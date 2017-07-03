package ua.dudka.hrm.domain.model;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.domain.model.Transaction;
import ua.dudka.account.domain.model.Transaction.Type;
import ua.dudka.account.domain.model.Wallet;
import ua.dudka.account.domain.model.vo.Transactions;
import ua.dudka.account.domain.model.vo.Wallets;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.domain.model.employee.Salary;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

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
    public void paySalaryShouldWithdrawSalaryAmountFromCompanyAccount() throws Exception {
        testCompany.paySalary(testEmployee);

        Salary salary = testEmployee.getSalary();
        Account testCompanyAccount = testCompany.getAccount();
        Wallet wallet = testCompanyAccount.getWallets().getByCurrency(salary.getCurrency());
        BigDecimal balanceAfter = wallet.getBalance();

        BigDecimal amount = salary.getAmount();

        assertEquals(ADMIN_BALANCE.subtract(amount), balanceAfter);

    }

    @Test
    public void paySalaryShouldIncreaseEmployeeAccountBalance() throws Exception {
        Salary salary = testEmployee.getSalary();
        Wallet wallet = testEmployee.getAccount().getWallets().getByCurrency(salary.getCurrency());
        BigDecimal employeeBalanceBefore = wallet.getBalance();

        testCompany.paySalary(testEmployee);

        BigDecimal employeeBalanceAfter = wallet.getBalance();
        BigDecimal salaryAmount = BigDecimal.valueOf(salary.getAmount().longValue());

        assertEquals(employeeBalanceBefore.add(salaryAmount), employeeBalanceAfter);
    }


    @Test
    public void paySalaryShouldAddWithdrawTransactionToCompanyAccount() throws Exception {
        testCompany.paySalary(testEmployee);

        Wallets wallets = testCompany.getAccount().getWallets();
        Wallet salaryWallet = wallets.getByCurrency(Currency.USD);
        Transactions transactions = salaryWallet.getTransactions();
        Transaction transaction = transactions.get(0);

        Salary salary = testEmployee.getSalary();
        assertEquals(salary.getAmount(), transaction.getAmount());
        assertEquals(Type.WITHDRAWAL, transaction.getType());
    }

    @Test
    public void paySalaryShouldAddRefillTransactionToEmployeeAccount() throws Exception {
        testCompany.paySalary(testEmployee);

        Wallets wallets = testEmployee.getAccount().getWallets();
        Wallet salaryWallet = wallets.getByCurrency(Currency.USD);
        Transactions transactions = salaryWallet.getTransactions();
        Transaction refillTransaction = transactions.get(0);

        assertEquals(testEmployee.getSalary().getAmount(), refillTransaction.getAmount());
        assertEquals(Type.REFILL, refillTransaction.getType());
    }
}