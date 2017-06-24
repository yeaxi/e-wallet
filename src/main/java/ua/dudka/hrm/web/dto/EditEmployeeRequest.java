package ua.dudka.hrm.web.dto;

import lombok.Value;
import ua.dudka.hrm.domain.model.employee.Salary;

/**
 * @author Rostislav Dudka
 */
@Value
public class EditEmployeeRequest {
    private final Integer employeeId;
    private final String newPosition;
    private final Salary newSalary;
}
