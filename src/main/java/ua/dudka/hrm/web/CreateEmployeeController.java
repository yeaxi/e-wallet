package ua.dudka.hrm.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.hrm.domain.service.exception.EmployeeExistsException;
import ua.dudka.hrm.application.CurrentCompanyReader;
import ua.dudka.hrm.domain.service.EmployeeCreator;
import ua.dudka.hrm.web.dto.CreateEmployeeRequest;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.hrm.domain.model.employee.Salary;

import java.math.BigDecimal;

import static ua.dudka.hrm.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_PAGE_URL;
import static ua.dudka.hrm.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class CreateEmployeeController {

    private final CurrentCompanyReader currentCompanyReader;
    private final EmployeeCreator employeeCreator;

    @GetMapping(CREATE_EMPLOYEE_PAGE_URL)
    public String getPage(Model model) {
        model.addAttribute("company", currentCompanyReader.read());
        return getPathToPage();
    }

    @PostMapping(CREATE_EMPLOYEE_URL)
    public String createEmployee(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("position") String position,
            @RequestParam("salaryAmount") BigDecimal salaryAmount,
            @RequestParam("salaryCurrency") Currency salaryCurrency,
            Model model
    ) {
        CreateEmployeeRequest request = CreateEmployeeRequest.builder().name(name).surname(surname).email(email)
                .phoneNumber(phoneNumber).position(position).salary(Salary.of(salaryAmount, salaryCurrency))
                .build();
        log.info("handling request: #{}", request);

        try {
            employeeCreator.create(request);
            model.addAttribute("success", "");
        } catch (EmployeeExistsException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("company", currentCompanyReader.read());
        return getPathToPage();
    }

    private String getPathToPage() {
        return "hrm/create-employee";
    }

    public static class Links {
        public static final String ADMIN_BASE_URL = "/hrm";
        public static final String CREATE_EMPLOYEE_PAGE_URL = ADMIN_BASE_URL + "/create-employee";
        public static final String CREATE_EMPLOYEE_URL = CREATE_EMPLOYEE_PAGE_URL + "/send";
    }
}