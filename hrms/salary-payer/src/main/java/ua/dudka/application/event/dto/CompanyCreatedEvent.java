package ua.dudka.application.event.dto;

import lombok.Value;

/**
 * @author Rostislav Dudka
 */
@Value
public class CompanyCreatedEvent {
    private final int companyId;
}