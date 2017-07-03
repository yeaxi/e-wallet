package ua.dudka.salary_payer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ua.dudka.account.web.dto.MoneyTransferRequest;
import ua.dudka.hrm.domain.model.company.Company;
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.domain.model.employee.Salary;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class SalaryPayerImpl implements SalaryPayer {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void paySalary(Company company) {
        company.getEmployees().forEach(e -> sendPaySalaryEvent(company, e));
    }

    private void sendPaySalaryEvent(Company company, Employee employee) {
        MoneyTransferRequest request = buildRequest(company, employee);

        eventPublisher.publishEvent(request);
    }

    private MoneyTransferRequest buildRequest(Company company, Employee employee) {
        int companyId = company.getId();
        int employeeId = employee.getId();
        Salary salary = employee.getSalary();
        return new MoneyTransferRequest(salary.getAmount(), salary.getCurrency(), companyId, employeeId);
    }
}