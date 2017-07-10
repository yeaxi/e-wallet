package ua.dudka.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.dudka.application.config.AccountConfig;
import ua.dudka.domain.model.Account;
import ua.dudka.repository.AccountRepository;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class CurrentAccountReaderImpl implements CurrentAccountReader {

    private final AccountRepository accountRepository;

    @Override
    public Account read() {
        String email;
        try {
            email = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            //possible only if no-security profile
            email = AccountConfig.DEV_ACCOUNT_EMAIL;
        }
        return accountRepository.findByEmail(email).get();
    }
}