package ua.dudka.application.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.application.event.SalaryPayerChannels;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("cloud")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "ua.dudka.application.reader")
@EnableBinding(SalaryPayerChannels.class)
@EnableCircuitBreaker
public class CloudConfig {
}