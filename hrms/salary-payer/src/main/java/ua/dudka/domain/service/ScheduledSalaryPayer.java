package ua.dudka.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.dudka.application.reader.ReliableCompanyReader;

/**
 * @author Rostislav Dudka
 */
@Profile("scheduling")
@Service
@RequiredArgsConstructor
public class ScheduledSalaryPayer {

    private static final long SECOND = 1000;

    private final ReliableCompanyReader companyReader;
    private final SalaryPayer salaryPayer;


    @Scheduled(fixedDelay = SECOND)
    void payMonthlySalary() {
        companyReader.readAll().forEach(salaryPayer::paySalary);
    }
}