package ua.dudka.salary_payer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.dudka.hrm.repository.CompanyRepository;

/**
 * @author Rostislav Dudka
 */
@Profile("pay-salary")
@Service
@RequiredArgsConstructor
public class MonthlySalaryPayer {

    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;

    private final CompanyRepository companyRepository;
    private final SalaryPayer salaryPayer;


    @Scheduled(fixedDelay = SECOND)
    void payMonthlySalary() {
        companyRepository.findAll().forEach(salaryPayer::paySalary);
    }
}