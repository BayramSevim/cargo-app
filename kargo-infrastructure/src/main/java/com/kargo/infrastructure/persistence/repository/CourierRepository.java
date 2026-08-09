    package com.kargo.infrastructure.persistence.repository;

import com.kargo.infrastructure.persistence.entity.BranchEntity;
import com.kargo.infrastructure.persistence.entity.CourierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface CourierRepository extends JpaRepository<CourierEntity, Long> {
    }
