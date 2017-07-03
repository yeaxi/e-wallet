package ua.dudka.account.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ua.dudka.account.domain.model.vo.Wallets;

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
    @GeneratedValue
    private Integer number = 0;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Wallet> wallets = new HashSet<>();

    public Account() {
        initWallets(BigDecimal.ZERO);
    }

    public Account(BigDecimal initialBalance) {
        initWallets(initialBalance);
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


    public void applyTransaction(Transaction transaction) {
        Wallet wallet = getWallets().getByCurrency(transaction.getCurrency());
        wallet.applyTransaction(transaction);
    }
}