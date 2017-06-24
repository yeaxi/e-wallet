package ua.dudka.hrm.domain.service;

import ua.dudka.hrm.web.dto.EditEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
public interface EmployeeEditor {

    void edit(EditEmployeeRequest request);
}
