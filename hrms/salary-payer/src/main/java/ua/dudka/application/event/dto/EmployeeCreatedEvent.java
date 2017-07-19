package ua.dudka.application.event.dto;

import lombok.Value;
import ua.dudka.domain.model.Salary;

/**
 * @author Rostislav Dudka
 */
@Value
public class EmployeeCreatedEvent {
    private final int employeeId;
    private final Salary salary;
    private final int companyId;

}