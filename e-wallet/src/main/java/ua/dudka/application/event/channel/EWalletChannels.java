package ua.dudka.application.event.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EWalletChannels {

    String USER_CREATED_CHANNEL_NAME = "user_created_channel";

    String TRANSFER_MONEY_CHANNEL_NAME = "transfer_money_channel";

    @Input
    SubscribableChannel user_created_channel();

    @Input
    SubscribableChannel transfer_money_channel();
}