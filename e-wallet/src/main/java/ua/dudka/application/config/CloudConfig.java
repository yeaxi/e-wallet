package ua.dudka.application.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.application.event.channel.EWalletChannels;

/**
 * @author Rostislav Dudka
 */
@Configuration
@Profile("cloud")
@EnableDiscoveryClient
@EnableBinding(EWalletChannels.class)
public class CloudConfig {
}
