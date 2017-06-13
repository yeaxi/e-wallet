package ua.dudka.admin.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.admin.exception.EmployeeNotFoundException;
import ua.dudka.admin.service.AdminReader;
import ua.dudka.admin.service.EmployeeEditor;
import ua.dudka.admin.web.dto.EditEmployeeRequest;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Salary;
import ua.dudka.employee.repository.EmployeeRepository;

import java.math.BigDecimal;

import static ua.dudka.admin.web.EditEmployeeController.Links.EDIT_EMPLOYEE_PAGE_URL;
import static ua.dudka.admin.web.EditEmployeeController.Links.EDIT_EMPLOYEE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class EditEmployeeController {

    private final AdminReader adminReader;
    private final EmployeeEditor employeeEditor;
    private final EmployeeRepository employeeRepository;

    @GetMapping(EDIT_EMPLOYEE_PAGE_URL)
    public String getPage(@PathVariable("id") Integer employeeId, Model model) {
        model.addAttribute("admin", adminReader.read());
        model.addAttribute("employee", employeeRepository.findOne(employeeId));
        return getPathToPage();
    }

    @PostMapping(EDIT_EMPLOYEE_URL)
    public String editEmployee(
            @RequestParam Integer employeeId,
            @RequestParam String newPosition,
            @RequestParam BigDecimal newSalaryAmount,
            @RequestParam Currency newSalaryCurrency,
            Model model
    ) {
        try {
            Salary newSalary = Salary.of(newSalaryAmount, newSalaryCurrency);
            EditEmployeeRequest request = new EditEmployeeRequest(employeeId, newPosition, newSalary);

            log.info("handling request  #{}", request);
            employeeEditor.edit(request);
            return "redirect:/admin/edit-employee/" + employeeId;
        } catch (EmployeeNotFoundException e) {
            model.addAttribute("admin", adminReader.read());
            model.addAttribute("employee", employeeRepository.findOne(employeeId));
            model.addAttribute("error", e.getMessage());
            return getPathToPage();
        }
    }

    private String getPathToPage() {
        return "admin/edit-employee";
    }

    public static class Links {
        public static final String EDIT_EMPLOYEE_PAGE_URL = "/admin/edit-employee/{id}";
        public static final String EDIT_EMPLOYEE_URL = "/admin/edit-employee/send";
    }
}