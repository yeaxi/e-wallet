package ua.dudka.employee.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.exception.AccountNotFoundException;
import ua.dudka.employee.exception.MoneyTransferException;
import ua.dudka.employee.service.CurrentAccountReader;
import ua.dudka.employee.service.MoneyTransfer;
import ua.dudka.employee.web.dto.MoneyTransferRequest;

import java.math.BigDecimal;

import static ua.dudka.employee.web.MoneyTransferController.Links.MONEY_TRANSFER_PAGE_URL;
import static ua.dudka.employee.web.MoneyTransferController.Links.TRANSFER_MONEY_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
public class MoneyTransferController {

    private final CurrentAccountReader currentAccountReader;
    private final MoneyTransfer moneyTransfer;

    @GetMapping(MONEY_TRANSFER_PAGE_URL)
    public String getPage(Model model) {
        model.addAttribute("account", currentAccountReader.read());
        return getPathToPage();
    }

    private String getPathToPage() {
        return "user/money-transfer";
    }

    @PostMapping(TRANSFER_MONEY_URL)
    public String transfer(@RequestParam String amount,
                           @RequestParam Currency currency,
                           @RequestParam int destinationAccountNumber,
                           Model model
    ) {
        try {
            MoneyTransferRequest request = new MoneyTransferRequest(new BigDecimal(amount), currency, destinationAccountNumber);
            moneyTransfer.transfer(request);
        } catch (MoneyTransferException | AccountNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("account", currentAccountReader.read());
        return getPathToPage();
    }

    public static class Links {
        public static final String MONEY_TRANSFER_PAGE_URL = "/money-transfer";
        public static final String TRANSFER_MONEY_URL = MONEY_TRANSFER_PAGE_URL + "/send";

    }

}