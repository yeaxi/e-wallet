package ua.dudka.hrm.application.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.hrm.application.CompanyCreator;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.repository.CompanyRepository;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class CompanyConfig implements CommandLineRunner {

    public static final String ADMIN_USERNAME = "admin@admin.com";
    public static final String ADMIN_PASSWORD = "admin";
    private static final String DEV_COMPANY_MAIL = "UA_DUDKA@mail.com";

    @Getter
    private static Integer adminId = 0;

    private final CompanyRepository companyRepository;
    private final CompanyCreator companyCreator;

    @Override
    public void run(String... strings) throws Exception {
        clearCompanyRepository();
        createCompany();
    }

    private void clearCompanyRepository() {
        companyRepository.deleteAll();
    }

    private void createCompany() {
        Company company = companyCreator.create(DEV_COMPANY_MAIL);
        adminId = company.getId();
    }
}