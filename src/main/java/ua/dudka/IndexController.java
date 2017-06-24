package ua.dudka;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.dudka.config.AdminConfig;

import java.security.Principal;

import static ua.dudka.company.web.DisplayEmployeesController.Links.EMPLOYEES_PAGE_URL;
import static ua.dudka.employee.web.DashboardController.Links.DASHBOARD_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Principal principal) {
        if (principal != null && principal.getName().equals(AdminConfig.ADMIN_USERNAME))
            return "redirect:" + EMPLOYEES_PAGE_URL;
        else
            return "redirect:" + DASHBOARD_PAGE_URL;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}