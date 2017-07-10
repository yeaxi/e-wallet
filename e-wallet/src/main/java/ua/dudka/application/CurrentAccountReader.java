package ua.dudka.application;

import ua.dudka.domain.model.Account;

/**
 * @author Rostislav Dudka
 */
public interface CurrentAccountReader {
    Account read();
}