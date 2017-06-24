package ua.dudka.company.service;

import ua.dudka.company.web.dto.EditEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
public interface EmployeeEditor {

    void edit(EditEmployeeRequest request);
}
