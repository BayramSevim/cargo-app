package com.kargo.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "kargo.pricing")
public record PricingProperties(
        BigDecimal basePrice,
        Integer perKg,
        Integer currency
) {
}
