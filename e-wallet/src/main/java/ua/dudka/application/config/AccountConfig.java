package ua.dudka.application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.domain.model.Account;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.model.vo.MonetaryAmount;
import ua.dudka.repository.AccountRepository;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class AccountConfig implements CommandLineRunner {

    public static final String DEV_ACCOUNT_EMAIL = "devmail@mail.com";
    private final AccountRepository accountRepository;

    @Override
    public void run(String... strings) throws Exception {
        clearRepository();
        Account account = createAndSaveDevAccount();
        refillAccount(account);
    }

    private void clearRepository() {
        accountRepository.deleteAll();
    }

    private Account createAndSaveDevAccount() throws InterruptedException {
        int accountNumber = 101;
        String accountPassword = "dev";
        Account account = new Account(accountNumber, DEV_ACCOUNT_EMAIL, accountPassword);
        return accountRepository.save(account);
    }

    private void refillAccount(Account account) {
        MonetaryAmount amount = MonetaryAmount.of(BigDecimal.valueOf(10_000), Currency.USD);
        account.refill(amount);
        accountRepository.save(account);
    }
}