package ua.dudka.account.application.event.handler;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.account.application.event.dto.UserCreatedEvent;
import ua.dudka.account.domain.model.Account;
import ua.dudka.account.repository.AccountRepository;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Rostislav Dudka
 */
public class UserCreatedEventHandlerTest {
    private AccountRepository accountRepository;

    private UserCreatedEventHandler handler;

    @Before
    public void setUp() throws Exception {
        accountRepository = mock(AccountRepository.class);
        handler = new UserCreatedEventHandler(accountRepository);
    }

    @Test
    public void handleShouldCreateAccount() throws Exception {
        UserCreatedEvent event = new UserCreatedEvent(105, "mail");

        handler.handle(event);

        verify(accountRepository).save(eq(new Account(event.getUserId(), event.getEmail())));
    }
}