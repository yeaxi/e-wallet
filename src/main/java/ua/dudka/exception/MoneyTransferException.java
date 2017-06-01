package ua.dudka.exception;

/**
 * @author Rostislav Dudka
 */
public abstract class MoneyTransferException extends RuntimeException {
    public MoneyTransferException() {
    }

    public MoneyTransferException(String message) {
        super(message);
    }
}
