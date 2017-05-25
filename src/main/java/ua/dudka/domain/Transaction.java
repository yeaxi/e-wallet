package ua.dudka.domain;

/**
 * @author Rostislav Dudka
 */
public class Transaction {

    private Long id;

    private Double amount;

    private Type type;

    private Wallet.Currency currency;


    public enum Type {
        WITHDRAWAL, REFILL
    }
}