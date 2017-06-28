package ua.dudka.account.domain.model.vo;

import com.google.common.collect.ForwardingList;
import ua.dudka.account.domain.model.Currency;
import ua.dudka.account.domain.model.Wallet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Rostislav Dudka
 */
public class Wallets extends ForwardingList<Wallet> {
    private final List<Wallet> wallets = new ArrayList<>();

    public Wallets(Collection<Wallet> wallets) {
        this.wallets.addAll(wallets);
    }

    public Wallet getByCurrency(Currency currency) {
        return wallets.stream()
                .filter(w -> w.getCurrency().equals(currency))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("not found wallet by currency" + currency));
    }

    @Override
    protected List<Wallet> delegate() {
        return wallets;
    }
}