package ua.dudka.admin.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Salary;
import ua.dudka.admin.exception.EmployeeExistsException;
import ua.dudka.admin.service.AdminReader;
import ua.dudka.admin.service.EmployeeCreator;
import ua.dudka.admin.web.dto.CreateEmployeeRequest;

import static ua.dudka.admin.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_PAGE_URL;
import static ua.dudka.admin.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Log4j2
public class CreateEmployeeController {

    private final AdminReader adminReader;
    private final EmployeeCreator employeeCreator;

    @GetMapping(CREATE_EMPLOYEE_PAGE_URL)
    public String getPage(Model model) {
        model.addAttribute("admin", adminReader.read());
        return "/admin/create-employee";
    }

    @PostMapping(CREATE_EMPLOYEE_URL)
    public String createEmployee(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("position") String position,
            @RequestParam("salaryAmount") Integer salaryAmount,
            @RequestParam("salaryCurrency") Currency salaryCurrency,
            Model model
    ) {
        CreateEmployeeRequest request = CreateEmployeeRequest.builder().name(name).surname(surname).email(email)
                .phoneNumber(phoneNumber).position(position).salary(Salary.of(salaryAmount, salaryCurrency))
                .build();
        log.info("handling request: #{}", request);

        try {
            employeeCreator.create(request);
        } catch (EmployeeExistsException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("admin", adminReader.read());
        return "/admin/create-employee";
    }

    public static class Links {
        public static final String ADMIN_BASE_URL = "/admin";
        public static final String CREATE_EMPLOYEE_PAGE_URL = ADMIN_BASE_URL + "/create-employee";
        public static final String CREATE_EMPLOYEE_URL = CREATE_EMPLOYEE_PAGE_URL + "/send";
    }
}