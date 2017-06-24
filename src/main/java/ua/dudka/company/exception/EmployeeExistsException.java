package ua.dudka.company.exception;

/**
 * @author Rostislav Dudka
 */
public class EmployeeExistsException extends RuntimeException {
    public EmployeeExistsException(String message) {
        super(message);
    }
}
