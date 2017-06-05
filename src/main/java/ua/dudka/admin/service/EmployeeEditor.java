package ua.dudka.admin.service;

import ua.dudka.admin.web.dto.EditEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
public interface EmployeeEditor {

    void edit(EditEmployeeRequest request);
}
