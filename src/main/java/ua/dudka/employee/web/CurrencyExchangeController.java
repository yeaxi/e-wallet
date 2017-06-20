package ua.dudka.employee.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.exception.MoneyTransferException;
import ua.dudka.employee.service.CurrencyExchanger;
import ua.dudka.employee.service.CurrencyExchanger.ExchangeType;
import ua.dudka.employee.service.CurrentAccountReader;
import ua.dudka.employee.web.dto.CurrencyExchangeRequest;

import java.math.BigDecimal;

import static ua.dudka.employee.web.CurrencyExchangeController.Links.CURRENCY_EXCHANGE_PAGE_URL;
import static ua.dudka.employee.web.CurrencyExchangeController.Links.CURRENCY_EXCHANGE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeController {

    public static final String SUCCESS_MESSAGE_FORMAT = "successfully exchanged %s %s to %s";
    private final CurrentAccountReader currentAccountReader;
    private final CurrencyExchanger currencyExchanger;

    @GetMapping(CURRENCY_EXCHANGE_PAGE_URL)
    public String getPage(Model model) {
        addCurrentAccountToModel(model);
        return gePathToPage();
    }

    private String gePathToPage() {
        return "user/currency-exchange";
    }


    @PostMapping(CURRENCY_EXCHANGE_URL)
    public String exchangeCurrency(@RequestParam BigDecimal amount,
                                   @RequestParam Currency sellCurrency,
                                   @RequestParam Currency buyCurrency,
                                   @RequestParam ExchangeType type,
                                   Model model
    ) {

        try {
            processExchange(amount, sellCurrency, buyCurrency, type, model);
        } catch (MoneyTransferException e) {
            handleError(model, e);
        }
        addCurrentAccountToModel(model);
        return gePathToPage();
    }

    private void processExchange(BigDecimal amount, Currency sellCurrency, Currency buyCurrency, ExchangeType type, Model model) {
        CurrencyExchangeRequest request = new CurrencyExchangeRequest(amount, sellCurrency, buyCurrency, type);
        log.info("processing {}", request);

        currencyExchanger.exchange(request);
        addSuccessToModel(model, request);
    }

    private void addSuccessToModel(Model model, CurrencyExchangeRequest request) {
        model.addAttribute("success", String.format(SUCCESS_MESSAGE_FORMAT, request.getAmount(), request.getSellCurrency(), request.getBuyCurrency()));
    }

    private void addCurrentAccountToModel(Model model) {
        model.addAttribute("account", currentAccountReader.read());
    }

    private void handleError(Model model, MoneyTransferException e) {
        model.addAttribute("error", e.getMessage());
    }


    public static class Links {
        public static final String CURRENCY_EXCHANGE_PAGE_URL = "/currency-exchange";
        public static final String CURRENCY_EXCHANGE_URL = CURRENCY_EXCHANGE_PAGE_URL + "/send";

    }
}