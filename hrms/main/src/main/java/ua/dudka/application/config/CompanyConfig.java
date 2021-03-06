package ua.dudka.application.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.application.CompanyCreator;
import ua.dudka.domain.model.Company;
import ua.dudka.repository.CompanyRepository;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class CompanyConfig implements CommandLineRunner {

    static final String ADMIN_USERNAME = "admin@admin.com";
    static final String ADMIN_PASSWORD = "admin";
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