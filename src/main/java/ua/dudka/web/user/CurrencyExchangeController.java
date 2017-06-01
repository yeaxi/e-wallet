package ua.dudka.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.domain.Currency;
import ua.dudka.exception.MoneyTransferException;
import ua.dudka.service.AccountService;
import ua.dudka.service.CurrencyExchanger;
import ua.dudka.service.CurrencyExchanger.ExchangeType;
import ua.dudka.web.user.dto.CurrencyExchangeRequest;

import java.math.BigDecimal;

import static ua.dudka.web.user.CurrencyExchangeController.Links.CURRENCY_EXCHANGE_PAGE_URL;
import static ua.dudka.web.user.CurrencyExchangeController.Links.CURRENCY_EXCHANGE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final AccountService accountService;
    private final CurrencyExchanger currencyExchanger;

    @GetMapping(CURRENCY_EXCHANGE_PAGE_URL)
    public String getPage(Model model) {
        model.addAttribute("account", accountService.getCurrentAccount());
        return "/user/currency-exchange";
    }


    @PostMapping(CURRENCY_EXCHANGE_URL)
    public String sellCurrency(@RequestParam BigDecimal amount,
                               @RequestParam Currency sellCurrency,
                               @RequestParam Currency buyCurrency,
                               @RequestParam ExchangeType type,
                               Model model
    ) {

        try {
            currencyExchanger.exchange(new CurrencyExchangeRequest(amount, sellCurrency, buyCurrency, type));
        } catch (MoneyTransferException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("account", accountService.getCurrentAccount());
        return "/user/currency-exchange";
    }


    public static class Links {
        public static final String CURRENCY_EXCHANGE_PAGE_URL = "/currency-exchange";
        public static final String CURRENCY_EXCHANGE_URL = CURRENCY_EXCHANGE_PAGE_URL + "/send";

    }
}