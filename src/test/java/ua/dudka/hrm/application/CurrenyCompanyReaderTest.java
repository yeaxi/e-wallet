package ua.dudka.hrm.application;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.repository.CompanyRepository;
import ua.dudka.hrm.application.config.CompanyConfig;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class CurrenyCompanyReaderTest {

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