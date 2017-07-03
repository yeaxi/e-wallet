package ua.dudka.salary_payer;

import ua.dudka.hrm.domain.model.company.Company;

/**
 * @author Rostislav Dudka
 */
public interface SalaryPayer {
    void paySalary(Company company);
}