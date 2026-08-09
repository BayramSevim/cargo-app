package com.kargo.infrastructure.persistence.repository;

import com.kargo.infrastructure.persistence.entity.ShipmentEntity;
import com.kargo.infrastructure.persistence.entity.ShipmentEventEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, Long> , JpaSpecificationExecutor<ShipmentEntity> {
    @EntityGraph(attributePaths = {"events"})
    List<ShipmentEntity> findAll();
}
