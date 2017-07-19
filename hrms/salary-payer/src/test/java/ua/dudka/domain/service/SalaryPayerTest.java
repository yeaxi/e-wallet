package ua.dudka.domain.service;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.dudka.application.event.dto.TransferMoneyRequest;
import ua.dudka.application.event.sender.TransferMoneyRequestSender;
import ua.dudka.domain.model.Company;
import ua.dudka.domain.model.Employee;
import ua.dudka.domain.model.Salary;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Rostislav Dudka
 */
public class SalaryPayerTest {

    private static Company testCompany;
    private static Employee testEmployee;

    private static TransferMoneyRequestSender sender;

    private static SalaryPayer salaryPayer;


    @BeforeClass
    public static void setUp() throws Exception {
        testCompany = new Company();
        testEmployee = new Employee();
        testCompany.addEmployee(testEmployee);

        sender = mock(TransferMoneyRequestSender.class);

        salaryPayer = new SalaryPayerImpl(sender);
    }

    @Test
    public void paySalaryShouldSendPaySalaryEvent() throws Exception {
        salaryPayer.paySalary(testCompany);

        int companyId = testCompany.getId();
        int employeeId = testEmployee.getId();
        Salary salary = testEmployee.getSalary();
        TransferMoneyRequest event = new TransferMoneyRequest(salary.getAmount(), salary.getCurrency(), companyId, employeeId);

        verify(sender).send(eq(event));
    }
}