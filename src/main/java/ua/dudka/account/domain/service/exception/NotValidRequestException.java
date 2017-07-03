package ua.dudka.account.domain.service.exception;

/**
 * @author Rostislav Dudka
 */
public class NotValidRequestException extends MoneyTransferException {

    public NotValidRequestException(String message) {
        super(message);
    }
}
