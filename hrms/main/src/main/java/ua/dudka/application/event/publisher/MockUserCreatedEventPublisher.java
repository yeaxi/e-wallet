package ua.dudka.application.event.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.dudka.application.event.dto.UserCreatedEvent;

/**
 * @author Rostislav Dudka
 */
@Component
@Slf4j
public class MockUserCreatedEventPublisher implements UserCreatedEventPublisher {
    @Override
    public void publish(UserCreatedEvent event) {
        log.debug("mock publisher accepted {}", event);
    }
}
