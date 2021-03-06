package ua.dudka.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.dudka.domain.model.Currency;
import ua.dudka.domain.model.Wallet;
import ua.dudka.domain.model.vo.Transactions;

import static ua.dudka.web.DashboardController.Links.DASHBOARD_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
public class DashboardController extends AccountController {

    @GetMapping(DASHBOARD_PAGE_URL)
    public String getPage() {
        return "account/dashboard";
    }

    @ModelAttribute("transactions")
    public Transactions recentTransactions() {
        Wallet uahWallet = getCurrentAccount().getWallets().getByCurrency(Currency.UAH);
        return uahWallet.getTransactions().getRecent();
    }

    public static class Links {
        public static final String DASHBOARD_PAGE_URL = "/dashboard";
    }
}