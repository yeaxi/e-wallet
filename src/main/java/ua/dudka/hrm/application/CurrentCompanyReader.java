package ua.dudka.hrm.application;

import ua.dudka.hrm.domain.model.company.Company;

/**
 * @author Rostislav Dudka
 */
public interface CurrentCompanyReader {

    Company read();
}
