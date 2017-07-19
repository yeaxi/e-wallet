package ua.dudka.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dudka.repository.EmployeeRepository;

import static ua.dudka.web.DisplayEmployeesController.Links.EMPLOYEES_PAGE_URL;

/**
 * @author Rostislav Dudka
 */

@Controller
@RequiredArgsConstructor
public class DisplayEmployeesController extends HRMController {

    private final EmployeeRepository employeeRepository;

    @GetMapping(EMPLOYEES_PAGE_URL)
    public String getPage(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());

        return "hrm/employees";
    }

    public static class Links {
        public static final String EMPLOYEES_PAGE_URL = HRM_BASE_URL + "/employees";

    }
}