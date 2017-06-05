package ua.dudka.admin.web.dto;

import lombok.*;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Salary;

import java.math.BigDecimal;

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
    private Salary salary = Salary.of(BigDecimal.ZERO, Currency.UAH);
}