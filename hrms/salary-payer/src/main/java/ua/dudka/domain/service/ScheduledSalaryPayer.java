package ua.dudka.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.dudka.application.reader.CompanyReader;

/**
 * @author Rostislav Dudka
 */
@Profile("enable-scheduling")
@Service
@RequiredArgsConstructor
public class ScheduledSalaryPayer {

    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;

    private final CompanyReader companyReader;
    private final SalaryPayer salaryPayer;


    @Scheduled(fixedDelay = SECOND)
    void payMonthlySalary() {
        companyReader.readAll().forEach(salaryPayer::paySalary);
    }
}