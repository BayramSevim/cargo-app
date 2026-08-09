package com.kargo.infrastructure.persistence.repository;

import com.kargo.infrastructure.persistence.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, Long> , JpaSpecificationExecutor<ShipmentEntity> {
    @EntityGraph(attributePaths = {"events"})
    List<ShipmentEntity> findAll();

    @Query("SELECT s FROM ShipmentEntity s LEFT JOIN FETCH s.events WHERE s.id = :id")
    Optional<ShipmentEntity> findByIdWithEvents(@Param("id") Long id);
}
