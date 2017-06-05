package ua.dudka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("pay-salary")
@EnableScheduling
public class SalaryPaymentConfig {
}
