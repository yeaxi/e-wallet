package ua.dudka.employee.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dudka.employee.domain.Account;
import ua.dudka.employee.domain.Transaction;
import ua.dudka.employee.exception.AccountNotFoundException;
import ua.dudka.employee.exception.NotValidRequestException;
import ua.dudka.employee.repository.AccountRepository;
import ua.dudka.employee.service.CurrentAccountReader;
import ua.dudka.employee.service.MoneyTransfer;
import ua.dudka.employee.web.dto.MoneyTransferRequest;

import java.math.BigDecimal;

import static ua.dudka.employee.domain.Transaction.Type.REFILL;
import static ua.dudka.employee.domain.Transaction.Type.WITHDRAWAL;

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