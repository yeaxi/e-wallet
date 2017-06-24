package ua.dudka.salary_payer;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.repository.CompanyRepository;
import ua.dudka.hrm.application.CurrentCompanyReader;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.domain.model.employee.Salary;
import ua.dudka.hrm.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class MonthlySalaryPayerTest {

    private static CompanyRepository companyRepository;
    private static EmployeeRepository employeeRepository;
    private static CurrentCompanyReader currentCompanyReader;

    private static MonthlySalaryPayer salaryPayer;

    private static List<Employee> employees;
    private static Company testCompany;

    @BeforeClass
    public static void setUp() throws Exception {
        currentCompanyReader = mock(CurrentCompanyReader.class);
        testCompany = new Company(BigDecimal.valueOf(1_000_000));
        when(currentCompanyReader.read()).thenReturn(testCompany);

        companyRepository = mock(CompanyRepository.class);

        employeeRepository = mock(EmployeeRepository.class);

        employees = setUpEmployees();
        when(employeeRepository.findAll()).thenReturn(employees);

        salaryPayer = new MonthlySalaryPayer(currentCompanyReader, companyRepository, employeeRepository);
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

        verify(currentCompanyReader, times(employees.size())).read();
        verify(companyRepository, times(employees.size())).save(eq(testCompany));
        employees.forEach(e -> verify(employeeRepository).save(eq(e)));
    }
}