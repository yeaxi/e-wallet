package ua.dudka.company.service;

import ua.dudka.company.web.dto.CreateEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
public interface EmployeeCreator {

    void create(CreateEmployeeRequest request);
}