package com.kargo.infrastructure.persistence.repository;

import com.kargo.infrastructure.persistence.entity.AddressEntity;
import com.kargo.infrastructure.persistence.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}