package ua.dudka.application.config;

import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Service;
import ua.dudka.EWalletApplication;
import ua.dudka.domain.model.Account;
import ua.dudka.repository.AccountRepository;

import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("security")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    @Service
    @RequiredArgsConstructor
    private static class MyUserDetailsService implements UserDetailsService {
        private static final String MSG = "User with email %s not found!;";

        private final AccountRepository accountRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Account account = getAccount(email);
            List<GrantedAuthority> authorities = commaSeparatedStringToAuthorityList("USER");
            return new User(email, account.getPassword(), authorities);
        }

        private Account getAccount(String s) {
            return accountRepository.findByEmail(s)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format(MSG, s)));
        }
    }
}
