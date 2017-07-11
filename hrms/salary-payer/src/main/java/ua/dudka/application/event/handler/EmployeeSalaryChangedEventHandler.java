package ua.dudka.application.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.dudka.application.event.dto.EmployeeSalaryChangedEvent;
import ua.dudka.domain.model.Employee;
import ua.dudka.repository.EmployeeRepository;

/**
 * @author Rostislav Dudka
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeSalaryChangedEventHandler {

    private final EmployeeRepository employeeRepository;

    public void handle(EmployeeSalaryChangedEvent event) {
        logEvent(event);
        Employee employee = employeeRepository.findOne(event.getEmployeeId());
        employee.changeSalary(event.getSalary());
        employeeRepository.save(employee);
    }

    private void logEvent(EmployeeSalaryChangedEvent event) {
        log.debug("handling event {}", event);
    }
}