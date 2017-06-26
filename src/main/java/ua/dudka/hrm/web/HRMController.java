package ua.dudka.hrm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.dudka.hrm.application.CurrentCompanyReader;
import ua.dudka.hrm.domain.model.company.Company;

/**
 * @author Rostislav Dudka
 */
public abstract class HRMController {
    public static final String HRM_BASE_URL = "/hrm";

    @Autowired
    private CurrentCompanyReader currentCompanyReader;

    @ModelAttribute("company")
    public Company getCurrentCompany() {
        return currentCompanyReader.read();
    }


}