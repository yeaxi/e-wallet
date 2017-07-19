package ua.dudka.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.application.event.TransferMoneyRequest;
import ua.dudka.application.event.TransferMoneyRequestSender;
import ua.dudka.domain.model.Company;
import ua.dudka.domain.model.Employee;
import ua.dudka.domain.model.Salary;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class SalaryPayerImpl implements SalaryPayer {

    private final TransferMoneyRequestSender sender;

    @Override
    public void paySalary(Company company) {
        company.getEmployees().forEach(e -> paySalary(company, e));
    }

    private void paySalary(Company company, Employee employee) {
        TransferMoneyRequest request = buildRequest(company, employee);

        sender.send(request);
    }

    private TransferMoneyRequest buildRequest(Company company, Employee employee) {
        int companyId = company.getId();
        int employeeId = employee.getId();
        Salary salary = employee.getSalary();
        return new TransferMoneyRequest(salary.getAmount(), salary.getCurrency(), companyId, employeeId);
    }
}