package com.kargo.infrastructure.persistence.repository;

import com.kargo.infrastructure.persistence.entity.ShipmentEntity;
import com.kargo.infrastructure.persistence.entity.ShipmentEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentEventRepository extends JpaRepository<ShipmentEventEntity, Long> {
}
