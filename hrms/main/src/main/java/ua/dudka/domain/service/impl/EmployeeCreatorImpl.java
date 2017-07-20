package ua.dudka.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.application.CurrentCompanyReader;
import ua.dudka.application.event.dto.UserCreatedEvent;
import ua.dudka.application.event.publisher.UserCreatedEventPublisher;
import ua.dudka.domain.model.Company;
import ua.dudka.domain.model.Employee;
import ua.dudka.domain.service.EmployeeCreator;
import ua.dudka.domain.service.exception.EmployeeExistsException;
import ua.dudka.repository.EmployeeRepository;
import ua.dudka.web.dto.CreateEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class EmployeeCreatorImpl implements EmployeeCreator {

    private final EmployeeRepository employeeRepository;
    private final CurrentCompanyReader currentCompanyReader;
    private final UserCreatedEventPublisher publisher;

    @Override
    public void create(CreateEmployeeRequest request) {
        validate(request);

        Company company = currentCompanyReader.read();
        Employee employee = buildEmployee(request, company);

        employee = employeeRepository.save(employee);
        String password = employee.getEmail();
        publisher.publish(new UserCreatedEvent(employee.getId(), employee.getEmail(), password));
    }

    private void validate(CreateEmployeeRequest request) {
        if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmployeeExistsException("employee with email '" + request.getEmail() + "' already exists!");
        }
        if (employeeRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new EmployeeExistsException("employee with phone number '" + request.getPhoneNumber() + "' already exists!");
        }
    }

    private Employee buildEmployee(CreateEmployeeRequest request, Company company) {
        return Employee
                .builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .position(request.getPosition())
                .salary(request.getSalary())
                .company(company)
                .build();
    }
}