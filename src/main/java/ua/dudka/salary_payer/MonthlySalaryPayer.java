package ua.dudka.salary_payer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dudka.admin.domain.Admin;
import ua.dudka.admin.repository.AdminRepository;
import ua.dudka.admin.service.AdminReader;
import ua.dudka.employee.domain.Employee;
import ua.dudka.employee.repository.EmployeeRepository;

/**
 * @author Rostislav Dudka
 */

@Profile("pay-salary")
@Service
@RequiredArgsConstructor
public class MonthlySalaryPayer {

    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;
    private static final long FIVE_MINUTES_DELAY = 5 * MINUTE;

    private final AdminReader adminReader;
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;


    @Scheduled(fixedDelay = MINUTE)
    void payMonthlySalary() {
        employeeRepository.findAll().forEach(this::paySalary);
    }

    @Transactional
    private void paySalary(Employee employee) {
        Admin admin = adminReader.read();

        admin.paySalary(employee);

        adminRepository.save(admin);
        employeeRepository.save(employee);

    }
}