package ua.dudka.salary_payer;


import ua.dudka.domain.model.Company;

/**
 * @author Rostislav Dudka
 */
public interface SalaryPayer {
    void paySalary(Company company);
}