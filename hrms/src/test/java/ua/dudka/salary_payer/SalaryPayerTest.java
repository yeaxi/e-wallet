package ua.dudka.salary_payer;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationEventPublisher;
import ua.dudka.application.event.MoneyTransferRequest;
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

    private static ApplicationEventPublisher eventPublisher;

    private static SalaryPayer salaryPayer;


    @BeforeClass
    public static void setUp() throws Exception {
        testCompany = new Company();
        testEmployee = new Employee();
        testCompany.addEmployee(testEmployee);

        eventPublisher = mock(ApplicationEventPublisher.class);

        salaryPayer = new SalaryPayerImpl(eventPublisher);
    }

    @Test
    public void paySalaryShouldSendPaySalaryEvent() throws Exception {
        salaryPayer.paySalary(testCompany);

        int companyId = testCompany.getId();
        int employeeId = testEmployee.getId();
        Salary salary = testEmployee.getSalary();
        MoneyTransferRequest event = new MoneyTransferRequest(salary.getAmount(), salary.getCurrency(), companyId, employeeId);

        verify(eventPublisher).publishEvent(eq(event));
    }
}