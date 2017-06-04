package ua.dudka.employee.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.EWalletApplication;
import ua.dudka.employee.domain.Account;
import ua.dudka.employee.repository.AccountRepository;
import ua.dudka.employee.service.CurrentAccountReader;

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