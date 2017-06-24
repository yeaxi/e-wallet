package ua.dudka.hrm.domain.service;

import ua.dudka.hrm.web.dto.CreateEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
public interface EmployeeCreator {

    void create(CreateEmployeeRequest request);
}