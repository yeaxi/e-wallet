package ua.dudka.admin.service;

import ua.dudka.admin.web.dto.CreateEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
public interface EmployeeCreator {

    void create(CreateEmployeeRequest request);
}