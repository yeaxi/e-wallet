package ua.dudka.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.domain.model.Employee;
import ua.dudka.domain.service.EmployeeEditor;
import ua.dudka.domain.service.exception.EmployeeNotFoundException;
import ua.dudka.repository.EmployeeRepository;
import ua.dudka.web.dto.EditEmployeeRequest;

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