package ua.dudka.application.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import ua.dudka.application.event.dto.EmployeeSalaryChangedEvent;
import ua.dudka.domain.model.Employee;
import ua.dudka.repository.EmployeeRepository;

import static ua.dudka.application.event.channel.SalaryPayerChannels.EMPLOYEE_SALARY_CHANGED_CHANNEL_NAME;

/**
 * @author Rostislav Dudka
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeSalaryChangedEventHandler {

    private final EmployeeRepository employeeRepository;

    @StreamListener(EMPLOYEE_SALARY_CHANGED_CHANNEL_NAME)
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