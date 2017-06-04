package ua.dudka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.employee.domain.Account;
import ua.dudka.admin.domain.Admin;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Transaction;
import ua.dudka.employee.repository.AccountRepository;
import ua.dudka.admin.repository.AdminRepository;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@SpringBootApplication
public class EWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(EWalletApplication.class, args);
    }


    @Configuration
    @Profile("dev")
    @RequiredArgsConstructor
    public static class DevConfig implements CommandLineRunner {

        @Getter
        private static Integer devAccountNumber = 0;

        private final AccountRepository accountRepository;

        @Override
        public void run(String... strings) throws Exception {
            Account account = new Account();

            for (int i = 1; i < 5; i++) {
                account.applyTransaction(new Transaction(BigDecimal.valueOf(4.87 * i), Transaction.Type.REFILL, Currency.UAH));
                Thread.sleep(500);
            }
            account = accountRepository.save(account);
            devAccountNumber = account.getNumber();
        }

    }

    @Configuration
    @Profile("dev")
    @RequiredArgsConstructor
    public static class AdminConfig implements CommandLineRunner {

        @Getter
        private static Integer adminId = 0;

        private final AdminRepository adminRepository;

        @Override
        public void run(String... strings) throws Exception {
            Admin admin = new Admin(BigDecimal.valueOf(1000));

            admin = adminRepository.save(admin);
            adminId = admin.getId();
        }

    }
}