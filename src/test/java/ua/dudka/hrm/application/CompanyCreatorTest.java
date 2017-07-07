package ua.dudka.hrm.application;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationEventPublisher;
import ua.dudka.account.application.event.dto.UserCreatedEvent;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.repository.CompanyRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class CompanyCreatorTest {

    private static final String COMPANY_MAIL = "UA_DUDKA@mail.com";
    private CompanyRepository repository;
    private ApplicationEventPublisher publisher;

    private CompanyCreator creator;

    @Before
    public void setUp() throws Exception {
        repository = mock(CompanyRepository.class);
        when(repository.save(any(Company.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        publisher = mock(ApplicationEventPublisher.class);

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

        UserCreatedEvent event = new UserCreatedEvent(company.getId(), company.getEmail());
        verify(publisher).publishEvent(eq(event));
    }
}