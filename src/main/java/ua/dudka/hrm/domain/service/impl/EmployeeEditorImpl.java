package ua.dudka.hrm.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.hrm.domain.service.exception.EmployeeNotFoundException;
import ua.dudka.hrm.domain.service.EmployeeEditor;
import ua.dudka.hrm.web.dto.EditEmployeeRequest;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.repository.EmployeeRepository;

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