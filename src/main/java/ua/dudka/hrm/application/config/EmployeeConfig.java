package ua.dudka.hrm.application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.hrm.domain.model.employee.Salary;
import ua.dudka.hrm.domain.service.EmployeeCreator;
import ua.dudka.hrm.repository.EmployeeRepository;
import ua.dudka.hrm.web.dto.CreateEmployeeRequest;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class EmployeeConfig implements CommandLineRunner {

    private static final String DEV_EMPLOYEE_USERNAME = "dev-employee@mail.com";
    private final EmployeeCreator employeeCreator;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... strings) throws Exception {
        clearEmployeeRepository();
        initDevEmployee();
        initSecondaryEmployees();
    }

    private void clearEmployeeRepository() {
        employeeRepository.deleteAll();
    }

    private void initDevEmployee() throws InterruptedException {
        CreateEmployeeRequest devEmployee = CreateEmployeeRequest.builder()
                .name("name")
                .surname("surname")
                .email(DEV_EMPLOYEE_USERNAME)
                .phoneNumber("0500000000")
                .position("middle")
                .salary(Salary.of(BigDecimal.TEN, Currency.BTC))
                .build();

        employeeCreator.create(devEmployee);
    }

    private void initSecondaryEmployees() {
        for (int i = 1; i < 10; i++) {
            CreateEmployeeRequest request = CreateEmployeeRequest.builder()
                    .name("name" + i)
                    .surname("surname" + i)
                    .email("email" + i + "@mail.com")
                    .phoneNumber("00000" + i)
                    .position("Senior Java Developer" + i)
                    .salary(Salary.of(BigDecimal.valueOf(10_000 * i), Currency.UAH))
                    .build();
            employeeCreator.create(request);
        }
    }
}