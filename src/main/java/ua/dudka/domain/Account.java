package ua.dudka.domain;

/**
 * @author Rostislav Dudka
 */
public class Account {

    private Long id;

    private Wallet uahWallet = new Wallet(Wallet.Currency.UAH);
    private Wallet usdWallet = new Wallet(Wallet.Currency.USD);
    private Wallet eurWallet = new Wallet(Wallet.Currency.EUR);
    private Wallet bitWallet = new Wallet(Wallet.Currency.BITCOIN);

}