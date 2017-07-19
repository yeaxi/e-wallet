package ua.dudka.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ua.dudka.application.event.UserCreatedEvent;
import ua.dudka.domain.model.Company;
import ua.dudka.repository.CompanyRepository;

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
        String password = companyEmail;
        publisher.publishEvent(new UserCreatedEvent(company.getId(), companyEmail, password));
        return company;
    }
}