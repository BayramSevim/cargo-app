package com.kargo.api.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "kargo.pricing")
public record PricingProperties(
        BigDecimal basePrice,
        BigDecimal perKg,
        String currency
) {
}
