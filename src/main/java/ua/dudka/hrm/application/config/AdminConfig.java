package ua.dudka.hrm.application.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.repository.CompanyRepository;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class AdminConfig implements CommandLineRunner {

    @Getter
    private static Integer adminId = 0;

    public static final String ADMIN_USERNAME = "admin@admin.com";

    public static final String ADMIN_PASSWORD = "admin";

    private final CompanyRepository companyRepository;

    @Override
    public void run(String... strings) throws Exception {
        Company company = new Company(BigDecimal.valueOf(2_000_000));

        company = companyRepository.save(company);
        adminId = company.getId();
    }

}