package ua.dudka.employee.domain;

import lombok.*;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PACKAGE;

/**
 * @author Rostislav Dudka
 */
@Embeddable
@NoArgsConstructor(access = PACKAGE, force = true)
@RequiredArgsConstructor(staticName = "of")
@Getter
@EqualsAndHashCode
@ToString
public class Salary {
    private final Integer amount;
    private final Currency currency;
}