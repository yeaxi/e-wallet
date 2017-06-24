package ua.dudka.company.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dudka.company.service.AdminReader;
import ua.dudka.employee.repository.EmployeeRepository;

import static ua.dudka.company.web.DisplayEmployeesController.Links.EMPLOYEES_PAGE_URL;

/**
 * @author Rostislav Dudka
 */

@Controller
@RequiredArgsConstructor
public class DisplayEmployeesController {

    private final AdminReader adminReader;
    private final EmployeeRepository employeeRepository;

    @GetMapping(EMPLOYEES_PAGE_URL)
    public String getPage(Model model) {
        model.addAttribute("admin", adminReader.read());
        model.addAttribute("employees", employeeRepository.findAll());

        return "company/employees";
    }

    public static class Links {
        public static final String ADMIN_BASE_URL = "/company";
        public static final String EMPLOYEES_PAGE_URL = ADMIN_BASE_URL + "/employees";

    }
}