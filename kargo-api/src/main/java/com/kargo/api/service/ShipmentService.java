package com.kargo.api.service;

import com.kargo.domain.ShipmentStatus;
import com.kargo.infrastructure.persistence.entity.CustomerEntity;
import com.kargo.infrastructure.persistence.entity.ShipmentEntity;
import com.kargo.infrastructure.persistence.entity.ShipmentEventEntity;
import com.kargo.infrastructure.persistence.repository.CustomerRepository;
import com.kargo.infrastructure.persistence.repository.ShipmentEventRepository;
import com.kargo.infrastructure.persistence.repository.ShipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final ShipmentEventRepository shipmentEventRepository;
    private final CustomerRepository customerRepository;


    public ShipmentService(ShipmentEventRepository shipmentEventRepository, ShipmentRepository shipmentRepository, CustomerRepository customerRepository) {
        this.shipmentEventRepository = shipmentEventRepository;
        this.shipmentRepository = shipmentRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void gonderiOlustur(String trackingNumber){
        CustomerEntity findCustomer = customerRepository.findById(1L).orElseThrow();

        ShipmentEntity shipment = new ShipmentEntity(trackingNumber,
                ShipmentStatus.CREATED,
                List.of(),
                findCustomer,
                BigDecimal.valueOf(23.34),
                BigDecimal.valueOf(123),
                Instant.now());

        var entity = shipmentRepository.save(shipment);

        ShipmentEventEntity shipmentEventEntity = new ShipmentEventEntity(
                entity,
                ShipmentStatus.CREATED,
                "DENEME",
                Instant.now()
        );
        shipmentEventRepository.save(shipmentEventEntity);
    }

    @Transactional
    public void gonderiOlusturVePatla(String trackingNumber){
        CustomerEntity findCustomer = customerRepository.findById(1L).orElseThrow();

        ShipmentEntity shipment = new ShipmentEntity(trackingNumber,
                ShipmentStatus.CREATED,
                List.of(),
                findCustomer,
                BigDecimal.valueOf(23.34),
                BigDecimal.valueOf(123),
                Instant.now());

        var entity = shipmentRepository.save(shipment);

        ShipmentEventEntity shipmentEventEntity = new ShipmentEventEntity(
                entity,
                ShipmentStatus.CREATED,
                "DENEME",
                Instant.now()
        );
        shipmentEventRepository.save(shipmentEventEntity);
        throw new RuntimeException("Bilerek patlattim");
    }

    @Transactional(rollbackFor = Exception.class)
    public void gonderiOlusturVeCheckedPatla(String trackingNumber) throws Exception {
        CustomerEntity findCustomer = customerRepository.findById(1L).orElseThrow();

        ShipmentEntity shipment = new ShipmentEntity(trackingNumber,
                ShipmentStatus.CREATED,
                List.of(),
                findCustomer,
                BigDecimal.valueOf(23.34),
                BigDecimal.valueOf(123),
                Instant.now());

        var entity = shipmentRepository.save(shipment);

        ShipmentEventEntity shipmentEventEntity = new ShipmentEventEntity(
                entity,
                ShipmentStatus.CREATED,
                "DENEME",
                Instant.now()
        );
        shipmentEventRepository.save(shipmentEventEntity);
        throw new Exception("Checked patlattim");
    }
}
