package ua.dudka.account.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.domain.model.Transaction;
import ua.dudka.account.domain.service.exception.AccountNotFoundException;
import ua.dudka.account.domain.service.exception.NotValidRequestException;
import ua.dudka.account.application.CurrentAccountReader;
import ua.dudka.account.web.dto.MoneyTransferRequest;
import ua.dudka.account.repository.AccountRepository;
import ua.dudka.account.domain.service.MoneyTransfer;

import java.math.BigDecimal;

import static ua.dudka.account.domain.model.Transaction.Type.REFILL;
import static ua.dudka.account.domain.model.Transaction.Type.WITHDRAWAL;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MoneyTransferImpl implements MoneyTransfer {
    private final AccountRepository accountRepository;
    private final CurrentAccountReader currentAccountReader;

    @Override
    public void transfer(MoneyTransferRequest request) {
        validate(request);

        Account sourceAccount = currentAccountReader.read();
        sourceAccount.applyTransaction(new Transaction(request.getAmount(), WITHDRAWAL, request.getCurrency()));
        accountRepository.save(sourceAccount);

        Account destinationAccount = getDestinationAccount(request.getDestinationAccountNumber());
        destinationAccount.applyTransaction(new Transaction(request.getAmount(), REFILL, request.getCurrency()));
        accountRepository.save(destinationAccount);
    }

    private void validate(MoneyTransferRequest request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ONE) == -1) {
            throw new NotValidRequestException("Requested amount is invalid");
        } else if (request.getCurrency() == null) {
            throw new NotValidRequestException("Requested currency is invalid");
        } else if (request.getDestinationAccountNumber() < 1) {
            throw new NotValidRequestException("Destination account number is invalid");
        }
    }

    private Account getDestinationAccount(int accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account with number: " + accountNumber + " not found!"));
    }

}