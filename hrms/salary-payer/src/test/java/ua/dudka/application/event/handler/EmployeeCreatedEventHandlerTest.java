package ua.dudka.application.event.handler;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.application.event.dto.EmployeeCreatedEvent;
import ua.dudka.domain.model.Company;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.model.Employee;
import ua.dudka.domain.model.Salary;
import ua.dudka.repository.CompanyRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class EmployeeCreatedEventHandlerTest {

    private EmployeeCreatedEventHandler handler;

    private CompanyRepository companyRepository;

    private static final int COMPANY_ID = 1;

    @Before
    public void setUp() throws Exception {
        initRepository();
        initHandler();
    }

    private void initRepository() {
        companyRepository = mock(CompanyRepository.class);

        Company testCompany = new Company(COMPANY_ID);
        when(companyRepository.findOne(eq(COMPANY_ID))).thenReturn(testCompany);
    }

    private void initHandler() {
        handler = new EmployeeCreatedEventHandler(companyRepository);
    }

    @Test
    public void handleShouldSaveCreatedCompany() throws Exception {
        EmployeeCreatedEvent event = prepareEvent();

        handler.handle(event);

        Company expectedCompany = buildCompanyWithEmployee(event.getEmployeeId(), event.getSalary());
        verify(companyRepository).save(eq(expectedCompany));
    }

    private EmployeeCreatedEvent prepareEvent() {
        int employeeId = 1;
        Salary salary = Salary.of(BigDecimal.TEN, Currency.USD);
        return new EmployeeCreatedEvent(employeeId, salary, COMPANY_ID);
    }

    private Company buildCompanyWithEmployee(int employeeId, Salary salary) {
        Company company = new Company(COMPANY_ID);
        company.addEmployee(new Employee(employeeId, salary));
        return company;
    }

    @Test(expected = EntityNotFoundException.class)
    public void handleEventWithNonexistentCompanyShouldThrowException() throws Exception {
        EmployeeCreatedEvent event = prepareEventWithNonexistentCompany();

        handler.handle(event);
    }

    private EmployeeCreatedEvent prepareEventWithNonexistentCompany() {
        int employeeId = 1, nonexistentCompanyId = 404;
        Salary salary = Salary.of(BigDecimal.TEN, Currency.USD);
        return new EmployeeCreatedEvent(employeeId, salary, nonexistentCompanyId);
    }
}