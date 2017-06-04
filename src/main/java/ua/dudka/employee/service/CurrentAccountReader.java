package ua.dudka.employee.service;

import ua.dudka.employee.domain.Account;

/**
 * @author Rostislav Dudka
 */
public interface CurrentAccountReader {
    Account read();
}