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
import ua.dudka.hrm.domain.model.employee.Employee;
import ua.dudka.hrm.repository.EmployeeRepository;

import java.security.Principal;
import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;
import static ua.dudka.account.web.DashboardController.Links.DASHBOARD_PAGE_URL;
import static ua.dudka.hrm.application.config.AdminConfig.ADMIN_PASSWORD;
import static ua.dudka.hrm.application.config.AdminConfig.ADMIN_USERNAME;
import static ua.dudka.hrm.web.CreateEmployeeController.Links.ADMIN_BASE_URL;
import static ua.dudka.hrm.web.DisplayEmployeesController.Links.EMPLOYEES_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@SpringBootApplication
public class EWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(EWalletApplication.class, args);
    }

    @Configuration
    @Profile("security")
    @EnableWebSecurity
    @RequiredArgsConstructor
    public static class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                    .antMatchers(ADMIN_BASE_URL).hasRole("ADMIN")
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

            private final EmployeeRepository employeeRepository;

            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                if (ADMIN_USERNAME.equals(email)) {

                    List<GrantedAuthority> authorities = commaSeparatedStringToAuthorityList("ADMIN");
                    return new User(email, ADMIN_PASSWORD, authorities);
                } else {
                    Employee employee = getEmployee(email);
                    List<GrantedAuthority> authorities = commaSeparatedStringToAuthorityList("USER");
                    return new User(email, employee.getPassword(), authorities);
                }

            }

            private Employee getEmployee(String s) {
                return employeeRepository.findByEmail(s)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format(MSG, s)));
            }
        }
    }

    @Controller
    public static class IndexController {

        @RequestMapping("/")
        public String index(Principal principal) {
            if (principal != null && principal.getName().equals(ADMIN_USERNAME))
                return "redirect:" + EMPLOYEES_PAGE_URL;
            else
                return "redirect:" + DASHBOARD_PAGE_URL;
        }

        @GetMapping("/login")
        public String login() {
            return "login";
        }
    }
}