package ua.dudka.salary_payer;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.dudka.company.domain.Admin;
import ua.dudka.company.repository.AdminRepository;
import ua.dudka.company.service.AdminReader;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Employee;
import ua.dudka.employee.domain.Salary;
import ua.dudka.employee.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class MonthlySalaryPayerTest {

    private static AdminRepository adminRepository;
    private static EmployeeRepository employeeRepository;
    private static AdminReader adminReader;

    private static MonthlySalaryPayer salaryPayer;

    private static List<Employee> employees;
    private static Admin testAdmin;

    @BeforeClass
    public static void setUp() throws Exception {
        adminReader = mock(AdminReader.class);
        testAdmin = new Admin(BigDecimal.valueOf(1_000_000));
        when(adminReader.read()).thenReturn(testAdmin);

        adminRepository = mock(AdminRepository.class);

        employeeRepository = mock(EmployeeRepository.class);

        employees = setUpEmployees();
        when(employeeRepository.findAll()).thenReturn(employees);

        salaryPayer = new MonthlySalaryPayer(adminReader, adminRepository, employeeRepository);
    }

    private static List<Employee> setUpEmployees() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            employees.add(Employee.builder()
                    .name("Super name" + i)
                    .surname("Super surname" + i)
                    .email("pretty_long_email@mail.com" + i)
                    .phoneNumber("+38050000000" + i)
                    .position("Senior Java Developer" + i)
                    .salary(Salary.of(BigDecimal.valueOf(20_000 * i), Currency.UAH))
                    .build());
        }
        return employees;
    }

    @Test
    public void payMonthlySalaryShouldPaySalaryToAllEmployees() throws Exception {
        salaryPayer.payMonthlySalary();

        verify(adminReader, times(employees.size())).read();
        verify(adminRepository, times(employees.size())).save(eq(testAdmin));
        employees.forEach(e -> verify(employeeRepository).save(eq(e)));
    }
}