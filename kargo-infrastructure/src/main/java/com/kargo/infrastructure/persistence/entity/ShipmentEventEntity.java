package com.kargo.infrastructure.persistence.entity;

import com.kargo.domain.ShipmentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "shipment_events")
public class ShipmentEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id")
    private ShipmentEntity shipment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @Column(name = "note")
    private String note;

    @Column(name = "occurred_at")
    private Instant occurred_at;

    protected ShipmentEventEntity(){

    }

    public ShipmentEventEntity(
            ShipmentEntity shipment,
            ShipmentStatus status,
            String note,
            Instant occurred_at
    ) {
        this.shipment = shipment;
        this.status = status;
        this.note = note;
        this.occurred_at = occurred_at;
    }
}