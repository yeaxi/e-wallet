package ua.dudka.account.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.domain.service.CurrencyExchanger;
import ua.dudka.account.domain.service.exception.MoneyTransferException;
import ua.dudka.account.web.dto.CurrencyExchangeRequest;

import java.math.BigDecimal;

import static ua.dudka.account.web.CurrencyExchangeController.Links.CURRENCY_EXCHANGE_PAGE_URL;
import static ua.dudka.account.web.CurrencyExchangeController.Links.CURRENCY_EXCHANGE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeController extends AccountController {

    private static final String SUCCESS_MESSAGE_FORMAT = "successfully exchanged %s %s to %s";
    private final CurrencyExchanger currencyExchanger;

    @GetMapping(CURRENCY_EXCHANGE_PAGE_URL)
    public String getCurrencyExchangePage() {
        return getPathToPage();
    }

    @PostMapping(CURRENCY_EXCHANGE_URL)
    public String exchangeCurrency(CurrencyExchangeRequest request, Model model) {
        logRequest(request);
        try {
            processExchange(request, model);
        } catch (MoneyTransferException e) {
            handleError(model, e);
        }
        return getPathToPage();
    }

    private void logRequest(CurrencyExchangeRequest request) {
        log.info("processing {}", request);
    }

    private String getPathToPage() {
        return "account/currency-exchange";
    }

    private void processExchange(CurrencyExchangeRequest request, Model model) {
        currencyExchanger.exchange(request);
        addSuccessAttributeToModel(model, request);
    }

    private void addSuccessAttributeToModel(Model model, CurrencyExchangeRequest request) {
        BigDecimal amount = request.getAmount();
        Currency sellCurrency = request.getSellCurrency();
        Currency buyCurrency = request.getBuyCurrency();
        String formattedMessage = String.format(SUCCESS_MESSAGE_FORMAT, amount, sellCurrency, buyCurrency);
        model.addAttribute("success", formattedMessage);
    }

    private void handleError(Model model, MoneyTransferException e) {
        log.info("handling error {}", e.getMessage());
        model.addAttribute("error", e.getMessage());
    }

    public static class Links {
        public static final String CURRENCY_EXCHANGE_PAGE_URL = "/currency-exchange";
        public static final String CURRENCY_EXCHANGE_URL = CURRENCY_EXCHANGE_PAGE_URL + "/send";

    }
}