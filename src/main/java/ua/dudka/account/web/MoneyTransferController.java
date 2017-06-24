package ua.dudka.account.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.domain.service.exception.AccountNotFoundException;
import ua.dudka.account.domain.service.exception.MoneyTransferException;
import ua.dudka.account.application.CurrentAccountReader;
import ua.dudka.account.domain.service.MoneyTransfer;
import ua.dudka.account.web.dto.MoneyTransferRequest;

import java.math.BigDecimal;

import static ua.dudka.account.web.MoneyTransferController.Links.MONEY_TRANSFER_PAGE_URL;
import static ua.dudka.account.web.MoneyTransferController.Links.TRANSFER_MONEY_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class MoneyTransferController {

    public static final String SUCCESS_TRANSFER_MESSAGE = "Successfully sent %s %s to account №%s";
    private final CurrentAccountReader currentAccountReader;
    private final MoneyTransfer moneyTransfer;

    @GetMapping(MONEY_TRANSFER_PAGE_URL)
    public String getPage(Model model) {
        model.addAttribute("account", currentAccountReader.read());
        return getPathToPage();
    }

    private String getPathToPage() {
        return "account/money-transfer";
    }

    @PostMapping(TRANSFER_MONEY_URL)
    public String transfer(@ModelAttribute MoneyTransferRequest request,
                           Model model
    ) {
        try {
            logRequest(request);
            processTransfer(request);
            addSuccessTransferAttributeToModel(model, request);
        } catch (MoneyTransferException | AccountNotFoundException e) {
            handleError(model, e);
        }
        model.addAttribute("account", currentAccountReader.read());
        return getPathToPage();
    }

    private void logRequest(MoneyTransferRequest request) {
        log.info("processing {}", request);
    }

    private void processTransfer(MoneyTransferRequest request) {
        moneyTransfer.transfer(request);
    }

    private void addSuccessTransferAttributeToModel(Model model, MoneyTransferRequest request) {
        BigDecimal amount = request.getAmount();
        Currency currency = request.getCurrency();
        int accountNumber = request.getDestinationAccountNumber();
        String formattedMessage = String.format(SUCCESS_TRANSFER_MESSAGE, amount, currency, accountNumber);
        model.addAttribute("success", formattedMessage);
    }

    private void handleError(Model model, RuntimeException e) {
        model.addAttribute("error", e.getMessage());
        log.info(e.getMessage());
    }

    public static class Links {
        public static final String MONEY_TRANSFER_PAGE_URL = "/money-transfer";
        public static final String TRANSFER_MONEY_URL = MONEY_TRANSFER_PAGE_URL + "/send";

    }

}