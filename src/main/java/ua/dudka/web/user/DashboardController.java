package ua.dudka.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dudka.service.CurrentAccountReader;

import static ua.dudka.web.user.DashboardController.Links.DASHBOARD_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final CurrentAccountReader currentAccountReader;

    @GetMapping(DASHBOARD_PAGE_URL)
    public String getPage(Model model) {
        model.addAttribute("account", currentAccountReader.read());
        return "/user/dashboard";
    }


    public static class Links {
        public static final String DASHBOARD_PAGE_URL = "/dashboard";

    }
}
