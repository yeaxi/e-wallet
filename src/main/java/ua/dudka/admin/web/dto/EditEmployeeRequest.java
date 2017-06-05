package ua.dudka.admin.web.dto;

import lombok.Value;
import ua.dudka.employee.domain.Salary;

/**
 * @author Rostislav Dudka
 */
@Value
public class EditEmployeeRequest {
    private final Integer employeeId;
    private final String newPosition;
    private final Salary newSalary;
}
