package ua.dudka.application.event.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Rostislav Dudka
 */
public interface HRMChannels {

    String USER_CREATED_CHANNEL_NAME = "user_created_channel";

    @Output
    MessageChannel user_created_channel();
}