package ua.dudka.hrm.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ua.dudka.account.application.event.dto.UserCreatedEvent;
import ua.dudka.hrm.application.CurrentCompanyReader;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.domain.service.EmployeeCreator;
import ua.dudka.hrm.domain.service.exception.EmployeeExistsException;
import ua.dudka.hrm.repository.EmployeeRepository;
import ua.dudka.hrm.web.dto.CreateEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class EmployeeCreatorImpl implements EmployeeCreator {

    private final EmployeeRepository employeeRepository;
    private final CurrentCompanyReader currentCompanyReader;
    private final ApplicationEventPublisher publisher;

    @Override
    public void create(CreateEmployeeRequest request) {
        validate(request);

        Company company = currentCompanyReader.read();
        Employee employee = buildEmployee(request, company);

        employee = employeeRepository.save(employee);
        publisher.publishEvent(new UserCreatedEvent(employee.getId(), employee.getEmail()));
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