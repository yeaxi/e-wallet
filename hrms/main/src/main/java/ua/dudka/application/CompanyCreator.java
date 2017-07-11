package ua.dudka.application;


import ua.dudka.domain.model.Company;

/**
 * @author Rostislav Dudka
 */
public interface CompanyCreator {

    Company create(String companyEmail);
}