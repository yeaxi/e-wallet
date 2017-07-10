package ua.dudka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.dudka.domain.model.Account;
import ua.dudka.repository.AccountRepository;

import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;
import static ua.dudka.web.DashboardController.Links.DASHBOARD_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@SpringBootApplication
public class EWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(EWalletApplication.class, args);
    }

    @Controller
    public static class IndexController {

        @RequestMapping("/")
        public String index() {
            return "redirect:" + DASHBOARD_PAGE_URL;
        }

        @GetMapping("/login")
        public String login() {
            return "login";
        }
    }
}