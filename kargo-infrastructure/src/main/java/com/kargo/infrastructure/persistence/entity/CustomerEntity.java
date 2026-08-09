package com.kargo.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "created_at")
    private Instant created_at;

    protected CustomerEntity() {
    }

    public CustomerEntity(String full_name,String email, String phone, Instant created_at) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.created_at = created_at;
    }
}
