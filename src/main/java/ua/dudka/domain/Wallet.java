package ua.dudka.domain;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rostislav Dudka
 */
@RequiredArgsConstructor
public class Wallet {

    private Double sum = 0D;

    private final Currency currency;

    private List<Transaction> transactions = new ArrayList<>();

    public enum Currency {
        UAH, USD, EUR, BITCOIN
    }
}