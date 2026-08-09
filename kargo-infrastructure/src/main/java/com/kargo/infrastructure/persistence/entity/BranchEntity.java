package com.kargo.infrastructure.persistence.entity;

import com.kargo.domain.ShipmentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "branches")
public class BranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "created_at")
    private Instant created_at;

    public BranchEntity( String name,String city, Instant created_at) {
        this.city = city;
        this.created_at = created_at;
        this.name = name;
    }

    protected BranchEntity() {
    }
}