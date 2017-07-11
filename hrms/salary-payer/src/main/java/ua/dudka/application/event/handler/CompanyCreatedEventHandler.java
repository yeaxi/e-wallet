package ua.dudka.application.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.dudka.application.event.dto.CompanyCreatedEvent;
import ua.dudka.domain.model.Company;
import ua.dudka.repository.CompanyRepository;

/**
 * @author Rostislav Dudka
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CompanyCreatedEventHandler {

    private final CompanyRepository companyRepository;

    public void handle(CompanyCreatedEvent event) {
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