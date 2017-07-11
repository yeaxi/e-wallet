package ua.dudka.domain.service;


import ua.dudka.domain.model.Company;

/**
 * @author Rostislav Dudka
 */
public interface SalaryPayer {
    void paySalary(Company company);
}