package ua.dudka.application;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.application.config.CompanyConfig;
import ua.dudka.domain.model.Company;
import ua.dudka.repository.CompanyRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class CurrentCompanyReaderTest {

    private static final int ADMIN_ID = CompanyConfig.getAdminId();
    private Company testCompany;

    private CompanyRepository companyRepository;
    private CurrentCompanyReader currentCompanyReader;

    @Before
    public void setUp() throws Exception {
        companyRepository = mock(CompanyRepository.class);

        testCompany = new Company();

        when(companyRepository.findOne(eq(ADMIN_ID))).thenReturn(testCompany);

        currentCompanyReader = new CurrentCompanyReaderImpl(companyRepository);
    }

    @Test
    public void adminReaderShouldRetrieveAdminFromRepository() throws Exception {
        Company company = currentCompanyReader.read();

        verify(companyRepository).findOne(eq(ADMIN_ID));

        assertEquals(testCompany, company);
    }

}