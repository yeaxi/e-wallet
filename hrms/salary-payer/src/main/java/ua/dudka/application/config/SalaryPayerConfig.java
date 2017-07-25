package ua.dudka.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("scheduling")
@EnableScheduling
public class SalaryPayerConfig {
}