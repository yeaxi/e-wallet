package ua.dudka.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ua.dudka.application.event.dto.UserCreatedEvent;
import ua.dudka.application.event.publisher.UserCreatedEventPublisher;
import ua.dudka.domain.model.Company;
import ua.dudka.repository.CompanyRepository;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class CompanyCreatorImpl implements CompanyCreator {
    private final CompanyRepository repository;
    private final UserCreatedEventPublisher publisher;

    @Override
    public Company create(String companyEmail) {
        Company company = repository.save(new Company(companyEmail));
        String password = companyEmail;
        publisher.publish(new UserCreatedEvent(company.getId(), companyEmail, password));
        return company;
    }
}