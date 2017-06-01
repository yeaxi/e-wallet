package ua.dudka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.EWalletApplication;
import ua.dudka.domain.Account;
import ua.dudka.repository.AccountRepository;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;


    @Override
    public Account getCurrentAccount() {
        return accountRepository.findOne(EWalletApplication.DevConfig.getDevAccountNumber());
    }
}
