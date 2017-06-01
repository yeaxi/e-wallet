package ua.dudka.exception;

/**
 * @author Rostislav Dudka
 */
public class NotValidRequestException extends MoneyTransferException {

    public NotValidRequestException() {
    }

    public NotValidRequestException(String message) {
        super(message);
    }
}
