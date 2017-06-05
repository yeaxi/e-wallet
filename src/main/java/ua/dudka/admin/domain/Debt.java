package ua.dudka.admin.domain;

import lombok.Value;
import ua.dudka.employee.domain.Salary;

import javax.persistence.Embeddable;

/**
 * @author Rostislav Dudka
 */
@Embeddable
@Value
public class Debt {
    private int employeeId;
    private Salary salary;

    public int getEmployeeId() {
        return employeeId;
    }

}
