    package com.kargo.infrastructure.persistence.repository;

import com.kargo.infrastructure.persistence.entity.CustomerEntity;
import com.kargo.infrastructure.persistence.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
