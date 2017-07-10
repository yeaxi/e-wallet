package ua.dudka.domain.service.exception;

/**
 * @author Rostislav Dudka
 */
public class NotValidRequestException extends MoneyTransferException {

    public NotValidRequestException(String message) {
        super(message);
    }
}
