package ua.dudka.application.event.dto;

import lombok.Value;

/**
 * @author Rostislav Dudka
 */
@Value
public class UserCreatedEvent {
    private final int id;
    private final String email;
    private final String password;
}
