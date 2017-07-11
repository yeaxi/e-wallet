package ua.dudka.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.dudka.domain.service.EmployeeEditor;
import ua.dudka.domain.service.exception.EmployeeNotFoundException;
import ua.dudka.repository.EmployeeRepository;
import ua.dudka.web.dto.EditEmployeeRequest;

import static ua.dudka.web.EditEmployeeController.Links.EDIT_EMPLOYEE_PAGE_URL;
import static ua.dudka.web.EditEmployeeController.Links.EDIT_EMPLOYEE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class EditEmployeeController extends HRMController {

    private final EmployeeEditor employeeEditor;
    private final EmployeeRepository employeeRepository;

    @GetMapping(EDIT_EMPLOYEE_PAGE_URL)
    public String getPage(@PathVariable("id") Integer employeeId, Model model) {
        setEmployeeToModel(employeeId, model);
        return getPathToPage();
    }

    @PostMapping(EDIT_EMPLOYEE_URL)
    public String editEmployee(EditEmployeeRequest request, Model model) {
        logRequest(request);
        try {
            processRequest(request, model);
        } catch (EmployeeNotFoundException e) {
            handleError(model, e);
        }
        setEmployeeToModel(request.getEmployeeId(), model);
        return getPathToPage();
    }

    private void setEmployeeToModel(Integer employeeId, Model model) {
        model.addAttribute("employee", employeeRepository.findOne(employeeId));
    }

    private String getPathToPage() {
        return "hrm/edit-employee";
    }

    private void logRequest(EditEmployeeRequest request) {
        log.info("handling request  #{}", request);
    }

    private void processRequest(EditEmployeeRequest request, Model model) {
        employeeEditor.edit(request);
        model.addAttribute("success", "");
    }

    private void handleError(Model model, EmployeeNotFoundException e) {
        model.addAttribute("error", e.getMessage());
    }

    public static class Links {
        public static final String EDIT_EMPLOYEE_PAGE_URL = HRM_BASE_URL + "/edit-employee/{id}";
        public static final String EDIT_EMPLOYEE_URL = HRM_BASE_URL + "/edit-employee/send";
    }
}