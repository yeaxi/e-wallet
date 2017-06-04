package ua.dudka.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.employee.domain.Employee;
import ua.dudka.admin.exception.EmployeeExistsException;
import ua.dudka.employee.repository.EmployeeRepository;
import ua.dudka.admin.service.EmployeeCreator;
import ua.dudka.admin.web.dto.CreateEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class EmployeeCreatorImpl implements EmployeeCreator {

    private final EmployeeRepository employeeRepository;

    @Override
    public void create(CreateEmployeeRequest request) {
        validate(request);

        Employee employee = buildEmployee(request);

        employeeRepository.save(employee);
    }

    private void validate(CreateEmployeeRequest request) {
        if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmployeeExistsException("employee with email '" + request.getEmail() + "' already exists!");
        }
        if (employeeRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new EmployeeExistsException("employee with phone number '" + request.getPhoneNumber() + "' already exists!");
        }
    }

    private Employee buildEmployee(CreateEmployeeRequest request) {
        return Employee
                .builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .position(request.getPosition())
                .salary(request.getSalary())
                .build();
    }
}