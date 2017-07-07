package ua.dudka.hrm.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ua.dudka.account.application.event.dto.UserCreatedEvent;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.repository.CompanyRepository;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class CompanyCreatorImpl implements CompanyCreator {
    private final CompanyRepository repository;
    private final ApplicationEventPublisher publisher;

    @Override
    public Company create(String companyEmail) {
        Company company = repository.save(new Company(companyEmail));
        publisher.publishEvent(new UserCreatedEvent(company.getId(), companyEmail));
        return company;
    }
}