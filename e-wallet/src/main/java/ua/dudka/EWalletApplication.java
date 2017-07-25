package ua.dudka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.dudka.application.event.channel.EWalletChannels;

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