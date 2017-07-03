package ua.dudka.account.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dudka.account.application.CurrentAccountReader;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.domain.model.vo.MonetaryAmount;
import ua.dudka.account.domain.service.MoneyTransfer;
import ua.dudka.account.domain.service.exception.AccountNotFoundException;
import ua.dudka.account.domain.service.exception.NotValidRequestException;
import ua.dudka.account.repository.AccountRepository;
import ua.dudka.account.web.dto.MoneyTransferRequest;

import java.math.BigDecimal;

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
        MonetaryAmount amount = MonetaryAmount.of(request.getAmount(), request.getCurrency());

        withdrawFromSourceAccount(amount);

        int destinationAccountNumber = request.getDestinationAccountNumber();
        refillDestinationAccount(amount, destinationAccountNumber);
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

    private void withdrawFromSourceAccount(MonetaryAmount amount) {
        Account sourceAccount = currentAccountReader.read();
        sourceAccount.withdraw(amount);
        accountRepository.save(sourceAccount);
    }

    private void refillDestinationAccount(MonetaryAmount amount, int destinationAccountNumber) {
        Account destinationAccount = getDestinationAccount(destinationAccountNumber);
        destinationAccount.refill(amount);
        accountRepository.save(destinationAccount);
    }

    private Account getDestinationAccount(int accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account with number: " + accountNumber + " not found!"));
    }

}