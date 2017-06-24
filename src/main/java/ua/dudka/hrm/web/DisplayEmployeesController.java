package ua.dudka.hrm.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dudka.hrm.application.CurrentCompanyReader;
import ua.dudka.hrm.repository.EmployeeRepository;

import static ua.dudka.hrm.web.DisplayEmployeesController.Links.EMPLOYEES_PAGE_URL;

/**
 * @author Rostislav Dudka
 */

@Controller
@RequiredArgsConstructor
public class DisplayEmployeesController {

    private final CurrentCompanyReader currentCompanyReader;
    private final EmployeeRepository employeeRepository;

    @GetMapping(EMPLOYEES_PAGE_URL)
    public String getPage(Model model) {
        model.addAttribute("company", currentCompanyReader.read());
        model.addAttribute("employees", employeeRepository.findAll());

        return "hrm/employees";
    }

    public static class Links {
        public static final String ADMIN_BASE_URL = "/hrm";
        public static final String EMPLOYEES_PAGE_URL = ADMIN_BASE_URL + "/employees";

    }
}