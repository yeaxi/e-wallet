package ua.dudka.application;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.application.event.dto.UserCreatedEvent;
import ua.dudka.application.event.publisher.UserCreatedEventPublisher;
import ua.dudka.domain.model.Company;
import ua.dudka.repository.CompanyRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class CompanyCreatorTest {

    private static final String COMPANY_MAIL = "UA_DUDKA@mail.com";
    private CompanyRepository repository;
    private UserCreatedEventPublisher publisher;

    private CompanyCreator creator;

    @Before
    public void setUp() throws Exception {
        repository = mock(CompanyRepository.class);
        when(repository.save(any(Company.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        publisher = mock(UserCreatedEventPublisher.class);

        creator = new CompanyCreatorImpl(repository, publisher);
    }

    @Test
    public void createCompanyShouldSaveItToRepository() throws Exception {
        Company company = creator.create(COMPANY_MAIL);

        verify(repository).save(eq(company));
    }

    @Test
    public void createCompanyPublishUserCreatedEvent() throws Exception {
        Company company = creator.create(COMPANY_MAIL);

        String password = company.getEmail();
        UserCreatedEvent event = new UserCreatedEvent(company.getId(), company.getEmail(), password);
        verify(publisher).publish(eq(event));
    }
}