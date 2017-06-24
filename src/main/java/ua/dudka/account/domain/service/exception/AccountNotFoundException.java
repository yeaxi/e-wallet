package ua.dudka.account.domain.service.exception;

/**
 * @author Rostislav Dudka
 */
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
