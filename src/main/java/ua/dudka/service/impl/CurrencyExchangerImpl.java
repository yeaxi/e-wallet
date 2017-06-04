package ua.dudka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dudka.domain.Account;
import ua.dudka.domain.Currency;
import ua.dudka.domain.Transaction;
import ua.dudka.exception.NotValidRequestException;
import ua.dudka.repository.AccountRepository;
import ua.dudka.service.CurrentAccountReader;
import ua.dudka.service.CurrencyExchanger;
import ua.dudka.service.CurrencyRates;
import ua.dudka.web.user.dto.CurrencyExchangeRequest;

import java.math.BigDecimal;

import static ua.dudka.domain.Transaction.Type.REFILL;
import static ua.dudka.domain.Transaction.Type.WITHDRAWAL;
import static ua.dudka.service.CurrencyExchanger.ExchangeType.BUY;
import static ua.dudka.service.CurrencyExchanger.ExchangeType.SELL;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CurrencyExchangerImpl implements CurrencyExchanger {
    private static final BigDecimal MIN_EXCHANGE_AMOUNT = BigDecimal.valueOf(0.000017);

    private final AccountRepository accountRepository;
    private final CurrentAccountReader currentAccountReader;
    private final CurrencyRates currencyRates;

    @Override
    public void exchange(CurrencyExchangeRequest request) {
        validate(request);
        Account currentAccount = currentAccountReader.read();

        Currency sellCurrency = request.getSellCurrency();
        Currency buyCurrency = request.getBuyCurrency();
        BigDecimal rate = currencyRates.getRate(sellCurrency, buyCurrency);

        BigDecimal amountToSell = getAmountToSell(request.getExchangeType(), request.getAmount(), rate);
        BigDecimal amountToBuy = getAmountToBuy(request.getExchangeType(), request.getAmount(), rate);

        currentAccount.applyTransaction(new Transaction(amountToSell, WITHDRAWAL, sellCurrency));
        currentAccount.applyTransaction(new Transaction(amountToBuy, REFILL, buyCurrency));

        accountRepository.save(currentAccount);
    }

    private void validate(CurrencyExchangeRequest request) {
        if (request.getAmount() == null || request.getAmount().compareTo(MIN_EXCHANGE_AMOUNT) == -1) {
            throw new NotValidRequestException("Exchange amount is invalid");
        } else if (request.getSellCurrency() == null) {
            throw new NotValidRequestException("Sell currency is invalid");
        } else if (request.getBuyCurrency() == null) {
            throw new NotValidRequestException("Buy currency is invalid");
        } else if (request.getExchangeType() == null) {
            throw new NotValidRequestException("Exchange type not specified");

        }
    }

    private BigDecimal getAmountToSell(ExchangeType type, BigDecimal amount, BigDecimal rate) {
        return type == SELL ? amount : amount.divide(rate, 2, BigDecimal.ROUND_UP);
    }

    private BigDecimal getAmountToBuy(ExchangeType type, BigDecimal amount, BigDecimal rate) {
        return type == BUY ? amount : amount.multiply(rate);
    }
}