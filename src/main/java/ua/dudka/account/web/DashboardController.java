package ua.dudka.account.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static ua.dudka.account.web.DashboardController.Links.DASHBOARD_PAGE_URL;

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

    public static class Links {
        public static final String DASHBOARD_PAGE_URL = "/dashboard";
    }
}