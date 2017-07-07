package ua.dudka.account.application.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ua.dudka.account.application.event.dto.UserCreatedEvent;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.repository.AccountRepository;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserCreatedEventHandler {
    private final AccountRepository accountRepository;

    @EventListener
    public void handle(UserCreatedEvent event) {
        log.info("handling event {}", event);
        accountRepository.save(new Account(event.getUserId(), event.getEmail()));
    }
}