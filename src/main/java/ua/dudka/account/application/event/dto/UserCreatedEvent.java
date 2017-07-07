package ua.dudka.account.application.event.dto;

import lombok.Value;

/**
 * @author Rostislav Dudka
 */
@Value
public class UserCreatedEvent {
    private final int userId;
    private final String email;
}