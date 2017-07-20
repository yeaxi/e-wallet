package ua.dudka.domain.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationEventPublisher;
import ua.dudka.application.CurrentCompanyReader;
import ua.dudka.application.event.dto.UserCreatedEvent;
import ua.dudka.application.event.publisher.UserCreatedEventPublisher;
import ua.dudka.domain.model.Company;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.model.Employee;
import ua.dudka.domain.model.Salary;
import ua.dudka.domain.service.exception.EmployeeExistsException;
import ua.dudka.domain.service.impl.EmployeeCreatorImpl;
import ua.dudka.repository.EmployeeRepository;
import ua.dudka.web.dto.CreateEmployeeRequest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class EmployeeCreatorTest {

    private static final String EXISTENT_EMAIL = "existent_email@email.com";
    private static final String EXISTENT_PHONE_NUMBER = "+380660000000";

    private static EmployeeRepository employeeRepository;
    private static CurrentCompanyReader companyReader;
    private static UserCreatedEventPublisher publisher;

    private static EmployeeCreator employeeCreator;

    @BeforeClass
    public static void setUp() throws Exception {
        setUpEmployeeRepository();
        setUpCompanyReader();
        setUpPublisher();
        setUpEmployeeCreator();
    }

    private static void setUpEmployeeRepository() {
        employeeRepository = mock(EmployeeRepository.class);

        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(employeeRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
        when(employeeRepository.findByEmail(eq(EXISTENT_EMAIL))).thenReturn(Optional.of(new Employee()));
        when(employeeRepository.findByPhoneNumber(eq(EXISTENT_PHONE_NUMBER))).thenReturn(Optional.of(new Employee()));
    }

    private static void setUpCompanyReader() {
        companyReader = mock(CurrentCompanyReader.class);
        when(companyReader.read()).thenReturn(new Company("company@mail.com"));
    }

    private static void setUpPublisher() {
        publisher = mock(UserCreatedEventPublisher.class);
    }

    private static void setUpEmployeeCreator() {
        employeeCreator = new EmployeeCreatorImpl(employeeRepository, companyReader, publisher);
    }

    @Test
    public void createShouldSaveNewEmployeeToRepository() throws Exception {
        CreateEmployeeRequest request = buildCreateEmployeeRequest();

        employeeCreator.create(request);

        Employee employee = buildEmployee(request);

        verify(employeeRepository, atLeastOnce()).save(eq(employee));
    }

    @Test
    public void createShouldPublishUserCreatedEvent() throws Exception {
        CreateEmployeeRequest request = buildCreateEmployeeRequest();

        employeeCreator.create(request);

        String password = request.getEmail();
        verify(publisher).publish(eq(new UserCreatedEvent(new Employee().getId(), request.getEmail(), password)));
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
                .company(companyReader.read())
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