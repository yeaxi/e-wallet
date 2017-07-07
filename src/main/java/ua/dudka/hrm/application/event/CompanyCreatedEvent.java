package ua.dudka.hrm.application.event;

import lombok.Value;

/**
 * @author Rostislav Dudka
 */
@Value
public class CompanyCreatedEvent {
    private final int companyId;
    private final String companyName;
}