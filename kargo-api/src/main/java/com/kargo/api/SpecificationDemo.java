package com.kargo.api;

import com.kargo.domain.ShipmentStatus;
import com.kargo.infrastructure.persistence.entity.ShipmentEntity;
import com.kargo.infrastructure.persistence.repository.ShipmentRepository;
import com.kargo.infrastructure.persistence.specification.ShipmentSpecifications;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

import static com.kargo.domain.ShipmentStatus.CREATED;
import static com.kargo.infrastructure.persistence.specification.ShipmentSpecifications.*;

@Component
@Order(3)
@Transactional
public class SpecificationDemo implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(SpecificationDemo.class);
    private ShipmentRepository shipmentRepository;

    public SpecificationDemo(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Specification<ShipmentEntity> spec1 = Specification.allOf(hasStatus(CREATED));
        Specification<ShipmentEntity> spec2 = Specification.allOf(hasBranch(1L));
        Specification<ShipmentEntity> spec3 = Specification.allOf(hasStatus(CREATED), hasBranch(1L));
        Specification<ShipmentEntity> spec4 = Specification.allOf(hasStatus(null), hasBranch(null), hasCourier(null), createdBetween(null, null));
        List<ShipmentEntity> sonuc1 = shipmentRepository.findAll(spec1);
        List<ShipmentEntity> sonuc2 = shipmentRepository.findAll(spec2);
        List<ShipmentEntity> sonuc3 = shipmentRepository.findAll(spec3);
        List<ShipmentEntity> sonuc4 = shipmentRepository.findAll(spec4);

        logger.info("Sadece CREATED: " + sonuc1.size());
        logger.info("Sadece BRANCH" + sonuc2.size());
        logger.info("CREATED ve BRANCH Birlikte: " + sonuc3.size());
        logger.info("Alayı Birlikte: " + sonuc4.size());
    }
}
