package ua.dudka.admin.web.dto;

import lombok.*;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Salary;

/**
 * @author Rostislav Dudka
 */
@AllArgsConstructor()
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class CreateEmployeeRequest {
    private String name = "";
    private String surname = "";
    private String email = "";
    private String phoneNumber = "";
    private String position = "";
    private Salary salary = Salary.of(0, Currency.UAH);
}