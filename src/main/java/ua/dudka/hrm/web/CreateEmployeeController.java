package ua.dudka.hrm.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.dudka.hrm.domain.service.EmployeeCreator;
import ua.dudka.hrm.domain.service.exception.EmployeeExistsException;
import ua.dudka.hrm.web.dto.CreateEmployeeRequest;

import static ua.dudka.hrm.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_PAGE_URL;
import static ua.dudka.hrm.web.CreateEmployeeController.Links.CREATE_EMPLOYEE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class CreateEmployeeController extends HRMController {

    private final EmployeeCreator employeeCreator;

    @GetMapping(CREATE_EMPLOYEE_PAGE_URL)
    public String getPage() {
        return getPathToPage();
    }

    @PostMapping(CREATE_EMPLOYEE_URL)
    public String createEmployee(CreateEmployeeRequest request, Model model) {
        logRequest(request);
        try {
            processRequest(request, model);
        } catch (EmployeeExistsException e) {
            logError(model, e);
        }
        return getPathToPage();
    }

    private void logRequest(CreateEmployeeRequest request) {
        log.info("handling request: {}", request);
    }

    private void processRequest(CreateEmployeeRequest request, Model model) {
        employeeCreator.create(request);
        model.addAttribute("success", "");
    }

    private void logError(Model model, Exception e) {
        log.info("handling EmployeeExistsException", e.getMessage());
        model.addAttribute("error", e.getMessage());
    }

    private String getPathToPage() {
        return "hrm/create-employee";
    }

    public static class Links {
        public static final String CREATE_EMPLOYEE_PAGE_URL = HRM_BASE_URL + "/create-employee";
        public static final String CREATE_EMPLOYEE_URL = CREATE_EMPLOYEE_PAGE_URL + "/send";
    }
}