package ua.dudka.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ua.dudka.domain.model.vo.MonetaryAmount;
import ua.dudka.domain.model.vo.Wallets;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rostislav Dudka
 */
@Entity
@Getter
@EqualsAndHashCode(of = "number")
@ToString
public class Account {

    @Id
    @Column(name = "id")
    private Integer number = 0;

    private String email;

    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Wallet> wallets = new HashSet<>();

    public Account() {
        initWallets(BigDecimal.ZERO);
    }

    public Account(BigDecimal initialBalance) {
        initWallets(initialBalance);
    }

    public Account(int userId, String email, String password) {
        this.number = userId;
        this.email = email;
        this.password = password;
        initWallets(BigDecimal.ZERO);
    }

    private void initWallets(BigDecimal initialBalance) {
        wallets.add(new Wallet(initialBalance, Currency.UAH));
        wallets.add(new Wallet(initialBalance, Currency.USD));
        wallets.add(new Wallet(initialBalance, Currency.EUR));
        wallets.add(new Wallet(initialBalance, Currency.BTC));
    }

    public Wallets getWallets() {
        return new Wallets(this.wallets);
    }


    public void refill(MonetaryAmount monetaryAmount) {
        Wallet wallet = getWallets().getByCurrency(monetaryAmount.getCurrency());
        wallet.refill(monetaryAmount.getAmount());
    }

    public void withdraw(MonetaryAmount monetaryAmount) {
        Wallet wallet = getWallets().getByCurrency(monetaryAmount.getCurrency());
        wallet.withdraw(monetaryAmount.getAmount());

    }
}