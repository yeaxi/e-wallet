package ua.dudka.domain.service;


import ua.dudka.web.dto.EditEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
public interface EmployeeEditor {

    void edit(EditEmployeeRequest request);
}
