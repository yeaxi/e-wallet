package ua.dudka.hrm.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.hrm.domain.service.exception.EmployeeNotFoundException;
import ua.dudka.hrm.application.CurrentCompanyReader;
import ua.dudka.hrm.domain.service.EmployeeEditor;
import ua.dudka.hrm.web.dto.EditEmployeeRequest;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.hrm.domain.model.employee.Salary;
import ua.dudka.hrm.repository.EmployeeRepository;

import java.math.BigDecimal;

import static ua.dudka.hrm.web.EditEmployeeController.Links.EDIT_EMPLOYEE_PAGE_URL;
import static ua.dudka.hrm.web.EditEmployeeController.Links.EDIT_EMPLOYEE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class EditEmployeeController {

    private final CurrentCompanyReader currentCompanyReader;
    private final EmployeeEditor employeeEditor;
    private final EmployeeRepository employeeRepository;

    @GetMapping(EDIT_EMPLOYEE_PAGE_URL)
    public String getPage(@PathVariable("id") Integer employeeId, Model model) {
        setDataToModel(employeeId, model);
        return getPathToPage();
    }

    private void setDataToModel(Integer employeeId, Model model) {
        model.addAttribute("company", currentCompanyReader.read());
        model.addAttribute("employee", employeeRepository.findOne(employeeId));
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
            processRequest(employeeId, newPosition, newSalaryAmount, newSalaryCurrency, model);
        } catch (EmployeeNotFoundException e) {
            handleError(model, e);
        }
        setDataToModel(employeeId, model);
        return getPathToPage();
    }

    private void processRequest(Integer employeeId, String newPosition, BigDecimal newSalaryAmount, Currency newSalaryCurrency, Model model) {
        Salary newSalary = Salary.of(newSalaryAmount, newSalaryCurrency);
        EditEmployeeRequest request = new EditEmployeeRequest(employeeId, newPosition, newSalary);

        log.info("handling request  #{}", request);
        employeeEditor.edit(request);

        model.addAttribute("success", "");
    }

    private void handleError(Model model, EmployeeNotFoundException e) {
        model.addAttribute("error", e.getMessage());
    }

    private String getPathToPage() {
        return "hrm/edit-employee";
    }

    public static class Links {
        public static final String EDIT_EMPLOYEE_PAGE_URL = "/hrm/edit-employee/{id}";
        public static final String EDIT_EMPLOYEE_URL = "/hrm/edit-employee/send";
    }
}