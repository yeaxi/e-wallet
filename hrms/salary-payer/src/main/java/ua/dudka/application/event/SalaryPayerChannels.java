package ua.dudka.application.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SalaryPayerChannels {

    String TRANSFER_MONEY_CHANNEL_NAME = "transfer_money_channel";

    @Output
    MessageChannel transfer_money_channel();
}