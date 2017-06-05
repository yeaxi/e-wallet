package ua.dudka.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.admin.exception.EmployeeNotFoundException;
import ua.dudka.admin.service.EmployeeEditor;
import ua.dudka.admin.web.dto.EditEmployeeRequest;
import ua.dudka.employee.domain.Employee;
import ua.dudka.employee.repository.EmployeeRepository;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class EmployeeEditorImpl implements EmployeeEditor {

    private final EmployeeRepository employeeRepository;

    @Override
    public void edit(EditEmployeeRequest request) {
        Employee employee = getEmployee(request.getEmployeeId());

        employee.changePosition(request.getNewPosition());
        employee.changeSalary(request.getNewSalary());

        employeeRepository.save(employee);
    }

    private Employee getEmployee(Integer employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }
}