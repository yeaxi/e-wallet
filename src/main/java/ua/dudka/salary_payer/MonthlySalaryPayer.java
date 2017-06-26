package ua.dudka.salary_payer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dudka.hrm.application.CurrentCompanyReader;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.repository.CompanyRepository;
import ua.dudka.hrm.repository.EmployeeRepository;

/**
 * @author Rostislav Dudka
 */

@Profile("pay-salary")
@Service
@RequiredArgsConstructor
public class MonthlySalaryPayer {

    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;

    private final CurrentCompanyReader currentCompanyReader;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;


    @Scheduled(fixedDelay = MINUTE)
    void payMonthlySalary() {
        employeeRepository.findAll().forEach(this::paySalary);
    }

    @Transactional
    private void paySalary(Employee employee) {
        Company company = currentCompanyReader.read();

        company.paySalary(employee);

        companyRepository.save(company);
        employeeRepository.save(employee);

    }
}