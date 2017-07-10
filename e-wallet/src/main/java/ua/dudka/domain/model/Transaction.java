package ua.dudka.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Rostislav Dudka
 */
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@Getter
@EqualsAndHashCode(exclude = {"number", "date"})
@ToString
public class Transaction {

    @Id
    private final String number = UUID.randomUUID().toString();
    private final BigDecimal amount;
    private final LocalDateTime date = LocalDateTime.now();
    private final Type type;
    private final BigDecimal balance;


    public enum Type {
        WITHDRAWAL, REFILL
    }
}