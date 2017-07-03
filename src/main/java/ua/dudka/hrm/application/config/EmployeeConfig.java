package ua.dudka.hrm.application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.domain.model.vo.MonetaryAmount;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.domain.model.employee.Salary;
import ua.dudka.hrm.repository.CompanyRepository;
import ua.dudka.hrm.repository.EmployeeRepository;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class EmployeeConfig implements CommandLineRunner {

    public static final String DEV_EMPLOYEE_USERNAME = "dev-employee@mail.com";

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... strings) throws Exception {
        clearAll();
        initDevEmployee();
        initSecondaryEmployees();
    }

    private void clearAll() {
        employeeRepository.deleteAll();
    }

    private void initDevEmployee() throws InterruptedException {
        Employee devEmployee = Employee.builder()
                .name("name")
                .surname("surname")
                .email(DEV_EMPLOYEE_USERNAME)
                .phoneNumber("0500000000")
                .position("middle")
                .salary(Salary.of(BigDecimal.TEN, Currency.BTC))
                .build();

        Account account = devEmployee.getAccount();
        for (int i = 1; i < 2; i++) {
            account.refill(MonetaryAmount.of(BigDecimal.valueOf(4.87 * i), Currency.UAH));
            Thread.sleep(500);
        }

        Company company = companyRepository.findOne(AdminConfig.getAdminId());
        company.addEmployee(devEmployee);
        companyRepository.save(company);
    }

    private void initSecondaryEmployees() {
        Company company = companyRepository.findOne(AdminConfig.getAdminId());
        for (int i = 1; i < 10; i++) {
            Employee employee = Employee.builder()
                    .name("name" + i)
                    .surname("surname" + i)
                    .email("email" + i + "@mail.com")
                    .phoneNumber("00000" + i)
                    .position("Senior Java Developer" + i)
                    .salary(Salary.of(BigDecimal.valueOf(10_000 * i), Currency.UAH))
                    .build();

            company.addEmployee(employee);
        }
        companyRepository.save(company);
    }
}