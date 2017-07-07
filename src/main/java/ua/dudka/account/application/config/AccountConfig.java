package ua.dudka.account.application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.repository.AccountRepository;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class AccountConfig implements CommandLineRunner {

    public static final String DEV_ACCOUNT_EMAIL = "devmail@mail.com";
    public static final int DEV_ACCOUNT_ID = 101;
    private final AccountRepository accountRepository;

    @Override
    public void run(String... strings) throws Exception {
        clearAccountsRepository();
        initDevAccount();
    }

    private void clearAccountsRepository() {
        accountRepository.deleteAll();
    }

    private void initDevAccount() throws InterruptedException {
        accountRepository.save(new Account(DEV_ACCOUNT_ID, DEV_ACCOUNT_EMAIL, BigDecimal.valueOf(10_000)));
    }
}