package com.kargo.infrastructure.persistence.entity;

import com.kargo.domain.ShipmentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "shipments")
public class ShipmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ShipmentStatus status;

    @OneToMany(mappedBy = "shipment")
    private List<ShipmentEventEntity> events;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private CourierEntity courier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchEntity branch;

    @Column(name = "weight_kg")
    private BigDecimal weightKg;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_at")
    private Instant createdAt;

    protected ShipmentEntity(){

    }

    public ShipmentEntity(
            String tracking_number,
            ShipmentStatus status,
            List<ShipmentEventEntity> events,
            CustomerEntity customer,
            BigDecimal weight_kg,
            BigDecimal price,
            Instant created_at
    ) {
        this.trackingNumber = tracking_number;
        this.status = status;
        this.events = events;
        this.customer = customer;
        this.weightKg = weight_kg;
        this.price = price;
        this.createdAt = created_at;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public List<ShipmentEventEntity> getEvents() {
        return events;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setBranch(BranchEntity branch) {
        this.branch = branch;
    }

    public void setCourier(CourierEntity courier) {
        this.courier = courier;
    }
}

