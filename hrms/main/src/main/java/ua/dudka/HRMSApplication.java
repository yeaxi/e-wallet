package ua.dudka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ua.dudka.web.DisplayEmployeesController.Links.EMPLOYEES_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@SpringBootApplication
@EnableDiscoveryClient
public class HRMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(HRMSApplication.class, args);
    }

    @Controller
    public static class IndexController {

        @RequestMapping("/")
        public String index() {
            return "redirect:" + EMPLOYEES_PAGE_URL;
        }

        @GetMapping("/login")
        public String login() {
            return "login";
        }
    }
}