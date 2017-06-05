package ua.dudka.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.admin.domain.Admin;
import ua.dudka.admin.repository.AdminRepository;

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

    private final AdminRepository adminRepository;

    @Override
    public void run(String... strings) throws Exception {
        Admin admin = new Admin(BigDecimal.valueOf(2_000_000));

        admin = adminRepository.save(admin);
        adminId = admin.getId();
    }

}