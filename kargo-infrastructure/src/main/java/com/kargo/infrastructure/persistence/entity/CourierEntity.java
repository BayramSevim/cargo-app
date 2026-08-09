package com.kargo.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "couriers")
public class CourierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "phone")
    private String phone;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchEntity branch_id;

    @Column(name = "created_at")
    private Instant created_at;

    public CourierEntity(String full_name, String phone, BranchEntity branch_id, Instant created_at) {
        this.branch_id = branch_id;
        this.created_at = created_at;
        this.full_name = full_name;
        this.phone = phone;
    }

    protected CourierEntity() {
    }
}
