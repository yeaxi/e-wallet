package ua.dudka.admin.service;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.admin.exception.EmployeeNotFoundException;
import ua.dudka.admin.service.impl.EmployeeEditorImpl;
import ua.dudka.admin.web.dto.EditEmployeeRequest;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Employee;
import ua.dudka.employee.domain.Salary;
import ua.dudka.employee.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class EmployeeEditorTest {

    private static final Integer NONEXISTENT_EMPLOYEE_ID = 404;
    private Employee testEmployee;

    private EmployeeRepository employeeRepository;
    private EmployeeEditor employeeEditor;


    @Before
    public void setUp() throws Exception {

        testEmployee = Employee.builder()
                .id(101)
                .name("name")
                .surname("surname")
                .email("mail@mail.com")
                .phoneNumber("005050550")
                .position("middle")
                .salary(Salary.of(BigDecimal.valueOf(2000), Currency.USD))
                .build();

        employeeRepository = mock(EmployeeRepository.class);
        when(employeeRepository.findById(eq(testEmployee.getId()))).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.findById(eq(NONEXISTENT_EMPLOYEE_ID))).thenReturn(Optional.empty());

        employeeEditor = new EmployeeEditorImpl(employeeRepository);
    }

    @Test
    public void editShouldChangeEmployeePositionAndSalary() throws Exception {
        EditEmployeeRequest request = buildEditEmployeeRequest();

        employeeEditor.edit(request);

        assertEquals(request.getNewPosition(), testEmployee.getPosition());
        assertEquals(request.getNewSalary(), testEmployee.getSalary());
    }

    private EditEmployeeRequest buildEditEmployeeRequest() {
        Salary salary = Salary.of(BigDecimal.valueOf(4000), Currency.USD);
        return new EditEmployeeRequest(testEmployee.getId(), "Senior", salary);
    }

    @Test
    public void editShouldSaveEmployeeToRepository() throws Exception {
        EditEmployeeRequest request = buildEditEmployeeRequest();

        employeeEditor.edit(request);

        verify(employeeRepository).save(eq(testEmployee));
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void ifRequestHasNonexistentEmployeeIdEditShouldThrowException() throws Exception {
        Salary salary = Salary.of(BigDecimal.valueOf(4000), Currency.USD);
        EditEmployeeRequest request = new EditEmployeeRequest(NONEXISTENT_EMPLOYEE_ID, "Senior", salary);

        employeeEditor.edit(request);
    }
}