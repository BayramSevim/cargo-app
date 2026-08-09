package com.kargo.infrastructure.persistence.entity;

import com.kargo.domain.AddressType;
import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id")
    private ShipmentEntity shipment;

    @Column(name = "address_type")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Column(name ="full_address")
    private String fullAddress;

    @Column(name ="city")
    private String city;

    @Column(name ="district")
    private String district;

    @Column(name ="postal_code")
    private String postalCode;

    protected AddressEntity() {
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public Long getId() {
        return id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public ShipmentEntity getShipment() {
        return shipment;
    }
}