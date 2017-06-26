package ua.dudka.hrm.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.hrm.domain.model.employee.Salary;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateEmployeeRequest {
    private String name = "";
    private String surname = "";
    private String email = "";
    private String phoneNumber = "";
    private String position = "";
    private Salary salary = Salary.of(BigDecimal.ZERO, Currency.UAH);
}