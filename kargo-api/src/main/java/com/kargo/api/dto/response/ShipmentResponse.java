package com.kargo.api.dto.response;

import com.kargo.domain.ShipmentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record ShipmentResponse(
        String trackingNumber,
        ShipmentStatus status,
        BigDecimal weightKg,
        BigDecimal price,
        Instant createdAt
) {
}
