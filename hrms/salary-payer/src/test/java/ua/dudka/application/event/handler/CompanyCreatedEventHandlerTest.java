package ua.dudka.application.event.handler;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.application.event.dto.CompanyCreatedEvent;
import ua.dudka.domain.model.Company;
import ua.dudka.repository.CompanyRepository;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Rostislav Dudka
 */
public class CompanyCreatedEventHandlerTest {

    private CompanyCreatedEventHandler handler;

    private CompanyRepository companyRepository;

    @Before
    public void setUp() throws Exception {
        companyRepository = mock(CompanyRepository.class);
        handler = new CompanyCreatedEventHandler(companyRepository);
    }

    @Test
    public void handleShouldSaveCreatedCompany() throws Exception {
        int companyId = 1;
        CompanyCreatedEvent event = new CompanyCreatedEvent(companyId);

        handler.handle(event);

        verify(companyRepository).save(eq(new Company(companyId)));
    }
}