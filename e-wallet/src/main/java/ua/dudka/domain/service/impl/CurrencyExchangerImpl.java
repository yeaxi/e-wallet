package ua.dudka.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dudka.application.CurrentAccountReader;
import ua.dudka.domain.model.Account;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.model.vo.MonetaryAmount;
import ua.dudka.domain.service.CurrencyExchanger;
import ua.dudka.domain.service.CurrencyRates;
import ua.dudka.domain.service.exception.NotValidRequestException;
import ua.dudka.repository.AccountRepository;
import ua.dudka.web.dto.CurrencyExchangeRequest;

import java.math.BigDecimal;

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

        currentAccount.withdraw(MonetaryAmount.of(amountToSell, sellCurrency));
        currentAccount.refill(MonetaryAmount.of(amountToBuy, buyCurrency));

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
        return type == ExchangeType.SELL ? amount : amount.divide(rate, 2, BigDecimal.ROUND_UP);
    }

    private BigDecimal getAmountToBuy(ExchangeType type, BigDecimal amount, BigDecimal rate) {
        return type == ExchangeType.BUY ? amount : amount.multiply(rate);
    }
}