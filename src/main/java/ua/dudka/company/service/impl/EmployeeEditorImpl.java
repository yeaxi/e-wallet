package ua.dudka.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.company.exception.EmployeeNotFoundException;
import ua.dudka.company.service.EmployeeEditor;
import ua.dudka.company.web.dto.EditEmployeeRequest;
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