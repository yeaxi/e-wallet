package ua.dudka.application.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import ua.dudka.application.event.dto.CompanyCreatedEvent;
import ua.dudka.domain.model.Company;
import ua.dudka.repository.CompanyRepository;

import static ua.dudka.application.event.channel.SalaryPayerChannels.COMPANY_CREATED_CHANNEL_NAME;

/**
 * @author Rostislav Dudka
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CompanyCreatedEventHandler {

    private final CompanyRepository companyRepository;

    @StreamListener(COMPANY_CREATED_CHANNEL_NAME)
    void handle(CompanyCreatedEvent event) {
        logEvent(event);
        Company company = createCompanyFrom(event);
        companyRepository.save(company);
    }

    private void logEvent(CompanyCreatedEvent event) {
        log.debug("handling event {}", event);
    }

    private Company createCompanyFrom(CompanyCreatedEvent event) {
        return new Company(event.getCompanyId());
    }
}