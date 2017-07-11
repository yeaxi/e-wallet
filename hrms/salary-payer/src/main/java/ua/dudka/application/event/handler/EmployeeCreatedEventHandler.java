package ua.dudka.application.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import ua.dudka.application.event.channel.SalaryPayerChannels;
import ua.dudka.application.event.dto.EmployeeCreatedEvent;
import ua.dudka.domain.model.Company;
import ua.dudka.domain.model.Employee;
import ua.dudka.repository.CompanyRepository;

import javax.persistence.EntityNotFoundException;

import static ua.dudka.application.event.channel.SalaryPayerChannels.EMPLOYEE_CREATED_CHANNEL_NAME;

/**
 * @author Rostislav Dudka
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeCreatedEventHandler {
    private final CompanyRepository companyRepository;

    @StreamListener(EMPLOYEE_CREATED_CHANNEL_NAME)
    public void handle(EmployeeCreatedEvent event) {
        logEvent(event);
        Company company = findCompanyOrThrowException(event);
        Employee employee = createEmployeeFrom(event);
        company.addEmployee(employee);

        companyRepository.save(company);
    }

    private void logEvent(EmployeeCreatedEvent event) {
        log.debug("handling event {}", event);
    }

    private Company findCompanyOrThrowException(EmployeeCreatedEvent event) {
        Company company = companyRepository.findOne(event.getCompanyId());
        if (company == null) {
            throw new EntityNotFoundException();
        }
        return company;
    }

    private Employee createEmployeeFrom(EmployeeCreatedEvent event) {
        return new Employee(event.getEmployeeId(), event.getSalary());
    }
}