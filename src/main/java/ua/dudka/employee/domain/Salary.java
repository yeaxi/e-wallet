package ua.dudka.employee.domain;

import lombok.*;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PACKAGE;

/**
 * @author Rostislav Dudka
 */
@Embeddable
@RequiredArgsConstructor(staticName = "of")
@Getter
@EqualsAndHashCode
@ToString
public class Salary {
    private final Integer amount;
    private final Currency currency;

    Salary() {
        this.amount = 0;
        this.currency = Currency.UAH;
    }
}