package ua.dudka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import ua.dudka.application.event.SalaryPayerChannels;

/**
 * @author Rostislav Dudka
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableBinding(SalaryPayerChannels.class)
@EnableCircuitBreaker
public class SalaryPayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalaryPayerApplication.class, args);
    }
}