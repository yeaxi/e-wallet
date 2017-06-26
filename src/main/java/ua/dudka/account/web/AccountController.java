package ua.dudka.account.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.dudka.account.application.CurrentAccountReader;
import ua.dudka.account.domain.model.Account;

/**
 * @author Rostislav Dudka
 */
public abstract class AccountController {

    @Autowired
    private CurrentAccountReader currentAccountReader;

    @ModelAttribute("account")
    public Account getCurrentAccount() {
        return currentAccountReader.read();
    }
}