package ua.dudka.domain.service;


import ua.dudka.web.dto.CreateEmployeeRequest;

/**
 * @author Rostislav Dudka
 */
public interface EmployeeCreator {

    void create(CreateEmployeeRequest request);
}