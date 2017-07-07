package ua.dudka.account.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.dudka.account.application.config.AccountConfig;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.repository.AccountRepository;
import ua.dudka.hrm.application.config.EmployeeConfig;

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