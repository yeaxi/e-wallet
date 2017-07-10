package ua.dudka.domain.service.exception;

/**
 * @author Rostislav Dudka
 */
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
