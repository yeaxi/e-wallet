package ua.dudka.application.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import ua.dudka.application.event.dto.UserCreatedEvent;
import ua.dudka.domain.model.Account;
import ua.dudka.repository.AccountRepository;

import static ua.dudka.application.event.channel.EWalletChannels.USER_CREATED_CHANNEL_NAME;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserCreatedEventHandler {
    private final AccountRepository accountRepository;

    @StreamListener(USER_CREATED_CHANNEL_NAME)
    void handle(UserCreatedEvent event) {
        log.debug("handling event {}", event);
        accountRepository.save(new Account(event.getId(), event.getEmail(), event.getPassword()));
    }
}