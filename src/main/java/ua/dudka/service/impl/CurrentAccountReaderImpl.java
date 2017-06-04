package ua.dudka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.EWalletApplication;
import ua.dudka.domain.Account;
import ua.dudka.repository.AccountRepository;
import ua.dudka.service.CurrentAccountReader;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class CurrentAccountReaderImpl implements CurrentAccountReader {

    private final AccountRepository accountRepository;

    @Override
    public Account read() {
        return accountRepository.findOne(EWalletApplication.DevConfig.getDevAccountNumber());
    }
}