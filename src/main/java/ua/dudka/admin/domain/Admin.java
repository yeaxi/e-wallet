package ua.dudka.admin.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.dudka.employee.domain.Currency;
import ua.dudka.employee.domain.Wallet;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rostislav Dudka
 */
@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Admin {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Wallet> wallets = new ArrayList<>();


    public Admin(BigDecimal initialBalance) {
        this.wallets.add(new Wallet(initialBalance, Currency.UAH));
        this.wallets.add(new Wallet(initialBalance, Currency.USD));
        this.wallets.add(new Wallet(initialBalance, Currency.EUR));
        this.wallets.add(new Wallet(initialBalance, Currency.BTC));
    }
}