package ua.dudka.account.application;

import ua.dudka.account.domain.model.Account;

/**
 * @author Rostislav Dudka
 */
public interface CurrentAccountReader {
    Account read();
}