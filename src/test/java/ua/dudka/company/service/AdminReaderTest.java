package ua.dudka.company.service;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.company.domain.Admin;
import ua.dudka.company.repository.AdminRepository;
import ua.dudka.company.service.impl.AdminReaderImpl;
import ua.dudka.config.AdminConfig;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class AdminReaderTest {

    private static final int ADMIN_ID = AdminConfig.getAdminId();
    private Admin testAdmin;

    private AdminRepository adminRepository;
    private AdminReader adminReader;

    @Before
    public void setUp() throws Exception {
        adminRepository = mock(AdminRepository.class);

        testAdmin = new Admin(BigDecimal.valueOf(1000));

        when(adminRepository.findOne(eq(ADMIN_ID))).thenReturn(testAdmin);

        adminReader = new AdminReaderImpl(adminRepository);
    }

    @Test
    public void adminReaderShouldRetrieveAdminFromRepository() throws Exception {
        Admin admin = adminReader.read();

        verify(adminRepository).findOne(eq(ADMIN_ID));

        assertEquals(testAdmin, admin);
    }

}