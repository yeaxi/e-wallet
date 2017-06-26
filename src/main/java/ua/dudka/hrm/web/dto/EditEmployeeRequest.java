package ua.dudka.hrm.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dudka.hrm.domain.model.employee.Salary;

/**
 * @author Rostislav Dudka
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditEmployeeRequest {
    private Integer employeeId;
    private String newPosition;
    private Salary newSalary = new Salary();
}
