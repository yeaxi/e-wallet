package ua.dudka.application.event.publisher;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ua.dudka.application.event.dto.UserCreatedEvent;

import static ua.dudka.application.event.channel.HRMChannels.USER_CREATED_CHANNEL_NAME;

/**
 * @author Rostislav Dudka
 */
@MessagingGateway
public interface UserCreatedEventPublisher {

    @Gateway(requestChannel = USER_CREATED_CHANNEL_NAME)
    void publish(UserCreatedEvent event);
}