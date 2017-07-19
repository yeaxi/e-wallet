package ua.dudka.application.event.handler;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.application.event.dto.EmployeeSalaryChangedEvent;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.model.Employee;
import ua.dudka.domain.model.Salary;
import ua.dudka.repository.EmployeeRepository;

import java.math.BigDecimal;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class EmployeeSalaryChangedEventHandlerTest {

    private EmployeeSalaryChangedEventHandler handler;
    private EmployeeRepository employeeRepository;

    private static final int EMPLOYEE_ID = 1;

    @Before
    public void setUp() throws Exception {
        initRepository();
        initHandler();
    }

    private void initRepository() {
        employeeRepository = mock(EmployeeRepository.class);

        Employee employee = new Employee(EMPLOYEE_ID, Salary.of(BigDecimal.ONE, Currency.UAH));
        when(employeeRepository.findOne(eq(EMPLOYEE_ID))).thenReturn(employee);
    }

    private void initHandler() {
        handler = new EmployeeSalaryChangedEventHandler(employeeRepository);
    }

    @Test
    public void handleShouldSaveCreatedCompany() throws Exception {
        EmployeeSalaryChangedEvent event = prepareEvent();

        handler.handle(event);

        Employee expectedEmployee = new Employee(event.getEmployeeId(), event.getSalary());
        verify(employeeRepository).save(eq(expectedEmployee));
    }

    private EmployeeSalaryChangedEvent prepareEvent() {
        Salary salary = Salary.of(BigDecimal.TEN, Currency.USD);
        return new EmployeeSalaryChangedEvent(EMPLOYEE_ID, salary);
    }
}