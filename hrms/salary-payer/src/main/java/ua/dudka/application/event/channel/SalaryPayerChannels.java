package ua.dudka.application.event.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SalaryPayerChannels {

    String TRANSFER_MONEY_CHANNEL_NAME = "transfer_money_channel";
    String COMPANY_CREATED_CHANNEL_NAME = "company_created_channel";
    String EMPLOYEE_CREATED_CHANNEL_NAME = "employee_created_channel";
    String EMPLOYEE_SALARY_CHANGED_CHANNEL_NAME = "employee_salary_changed_channel";

    @Output
    MessageChannel transfer_money_channel();

    @Input
    SubscribableChannel company_created_channel();

    @Input
    SubscribableChannel employee_created_channel();

    @Input
    SubscribableChannel employee_salary_changed_channel();
}