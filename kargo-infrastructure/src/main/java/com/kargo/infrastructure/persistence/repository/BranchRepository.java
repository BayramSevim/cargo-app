    package com.kargo.infrastructure.persistence.repository;

import com.kargo.infrastructure.persistence.entity.BranchEntity;
import com.kargo.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface BranchRepository extends JpaRepository<BranchEntity, Long> {
    }
