package ua.dudka.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.dudka.application.CurrentCompanyReader;
import ua.dudka.domain.model.Company;

/**
 * @author Rostislav Dudka
 */
public abstract class HRMController {
    static final String HRM_BASE_URL = "/hrm";

    @Autowired
    private CurrentCompanyReader currentCompanyReader;

    @ModelAttribute("company")
    public Company getCurrentCompany() {
        return currentCompanyReader.read();
    }
}