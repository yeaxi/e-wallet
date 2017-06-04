package ua.dudka.admin.service;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.admin.repository.AdminRepository;
import ua.dudka.EWalletApplication;
import ua.dudka.admin.domain.Admin;
import ua.dudka.admin.service.impl.AdminReaderImpl;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Rostislav Dudka
 */
public class AdminReaderTest {

    private static final int ADMIN_ID = EWalletApplication.AdminConfig.getAdminId();
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