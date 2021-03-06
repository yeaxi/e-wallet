package ua.dudka.domain.service.exception;

/**
 * @author Rostislav Dudka
 */
public class EmployeeNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Employee with such id does not exist!";

    public EmployeeNotFoundException() {
        super(MESSAGE);
    }
}
