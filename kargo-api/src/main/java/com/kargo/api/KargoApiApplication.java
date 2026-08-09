package com.kargo.api;

import com.kargo.api.config.PricingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties(PricingProperties.class)
@EntityScan("com.kargo.infrastructure.persistence.entity")
@EnableJpaRepositories("com.kargo.infrastructure.persistence.repository")
public class KargoApiApplication {
    private static final Logger log = LoggerFactory.getLogger(KargoApiApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(KargoApiApplication.class,args);
    }

    @Bean
    public CommandLineRunner log(PricingProperties pricingProperties){
        return args -> {
            log.info("Base Price:" + String.valueOf(pricingProperties.basePrice() + " TRY"));
            log.info("Per Kg:" + String.valueOf(pricingProperties.perKg()));
            log.info("Currency:" + String.valueOf(pricingProperties.currency()));
        };
    }
}


