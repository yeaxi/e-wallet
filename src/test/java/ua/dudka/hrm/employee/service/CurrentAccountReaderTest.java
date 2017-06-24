package ua.dudka.hrm.employee.service;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.account.application.CurrentAccountReader;
import ua.dudka.hrm.application.config.EmployeeConfig;
import ua.dudka.account.domain.model.Account;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.repository.EmployeeRepository;
import ua.dudka.account.application.CurrentAccountReaderImpl;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class CurrentAccountReaderTest {

    private static final String DEV_EMPLOYEE_EMAIL = EmployeeConfig.DEV_EMPLOYEE_USERNAME;
    private EmployeeRepository employeeRepository;
    private Account testAccount;


    private CurrentAccountReader currentAccountReader;

    @Before
    public void setUp() throws Exception {
        employeeRepository = mock(EmployeeRepository.class);

        testAccount = new Account();
        Employee employee = Employee.builder().account(testAccount).build();
        when(employeeRepository.findByEmail(eq(DEV_EMPLOYEE_EMAIL))).thenReturn(Optional.of(employee));

        currentAccountReader = new CurrentAccountReaderImpl(employeeRepository);
    }

    @Test
    public void getCurrentAccountShouldRetrieveAccountFromRepository() throws Exception {
        Account currentAccount = currentAccountReader.read();

        assertEquals(testAccount, currentAccount);

        verify(employeeRepository).findByEmail(eq(DEV_EMPLOYEE_EMAIL));
    }

}