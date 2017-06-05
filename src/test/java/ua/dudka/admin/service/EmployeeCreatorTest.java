package ua.dudka.admin.service;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.dudka.admin.service.impl.EmployeeCreatorImpl;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Employee;
import ua.dudka.employee.domain.Salary;
import ua.dudka.admin.exception.EmployeeExistsException;
import ua.dudka.employee.repository.EmployeeRepository;
import ua.dudka.admin.web.dto.CreateEmployeeRequest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Rostislav Dudka
 */
public class EmployeeCreatorTest {

    private static final String EXISTENT_EMAIL = "existent_email@email.com";
    private static final String EXISTENT_PHONE_NUMBER = "+380660000000";

    private static EmployeeRepository employeeRepository;

    private static EmployeeCreator employeeCreator;

    @BeforeClass
    public static void setUp() throws Exception {
        employeeRepository = mock(EmployeeRepository.class);

        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(employeeRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());

        when(employeeRepository.findByEmail(eq(EXISTENT_EMAIL))).thenReturn(Optional.of(new Employee()));
        when(employeeRepository.findByPhoneNumber(eq(EXISTENT_PHONE_NUMBER))).thenReturn(Optional.of(new Employee()));


        employeeCreator = new EmployeeCreatorImpl(employeeRepository);
    }

    @Test
    public void createShouldSaveNewEmployeeToRepository() throws Exception {
        CreateEmployeeRequest request = buildCreateEmployeeRequest();

        employeeCreator.create(request);

        Employee employee = buildEmployee(request);

        verify(employeeRepository).save(eq(employee));
    }

    private CreateEmployeeRequest buildCreateEmployeeRequest() {
        return CreateEmployeeRequest.builder()
                .name("name")
                .surname("surname")
                .email("email@mail.com")
                .phoneNumber("+380500000000")
                .position("position")
                .salary(Salary.of(BigDecimal.valueOf(3800), Currency.USD))
                .build();
    }

    private Employee buildEmployee(CreateEmployeeRequest request) {
        return Employee
                .builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .position(request.getPosition())
                .salary(request.getSalary())
                .build();
    }

    @Test(expected = EmployeeExistsException.class)
    public void createShouldThrowExceptionIfExistsEmployeeWithTheSameEmail() throws Exception {
        CreateEmployeeRequest request = CreateEmployeeRequest.builder().email(EXISTENT_EMAIL).phoneNumber("").build();

        employeeCreator.create(request);
    }

    @Test(expected = EmployeeExistsException.class)
    public void createShouldThrowExceptionIfExistsEmployeeWithTheSameNumber() throws Exception {
        CreateEmployeeRequest request = CreateEmployeeRequest.builder().email("").phoneNumber(EXISTENT_PHONE_NUMBER).build();

        employeeCreator.create(request);
    }
}