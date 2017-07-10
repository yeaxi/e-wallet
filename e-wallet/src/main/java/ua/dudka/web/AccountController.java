package ua.dudka.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.dudka.application.CurrentAccountReader;
import ua.dudka.domain.model.Account;

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