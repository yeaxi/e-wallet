package ua.dudka.account.domain.service.exception;

/**
 * @author Rostislav Dudka
 */
public abstract class MoneyTransferException extends RuntimeException {

    MoneyTransferException(String message) {
        super(message);
    }
}
