package com.kargo.api;

import com.kargo.infrastructure.persistence.entity.ShipmentEntity;
import com.kargo.infrastructure.persistence.entity.ShipmentEventEntity;
import com.kargo.infrastructure.persistence.repository.ShipmentRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(2)
public class NPlusOneDemo implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(NPlusOneDemo.class);

    private EntityManagerFactory entityManagerFactory;
    private ShipmentRepository shipmentRepository;

    public NPlusOneDemo(EntityManagerFactory entityManagerFactory, ShipmentRepository shipmentRepository) {
        this.entityManagerFactory = entityManagerFactory;
        this.shipmentRepository = shipmentRepository;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        entityManagerFactory.unwrap(SessionFactory.class).getStatistics().clear();
        List<ShipmentEntity> shipments = shipmentRepository.findAll();
        List<Integer> eventCount =  shipments.stream()
                .map(shipmentEntity -> shipmentEntity.getEvents().size())
                .toList();

        logger.info(String.valueOf(entityManagerFactory.unwrap(SessionFactory.class).getStatistics().getPrepareStatementCount()));
    }
}
