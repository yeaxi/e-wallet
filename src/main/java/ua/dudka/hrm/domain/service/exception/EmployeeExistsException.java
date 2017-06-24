package ua.dudka.hrm.domain.service.exception;

/**
 * @author Rostislav Dudka
 */
public class EmployeeExistsException extends RuntimeException {
    public EmployeeExistsException(String message) {
        super(message);
    }
}
