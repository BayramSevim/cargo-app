package com.kargo.api;

import com.kargo.infrastructure.persistence.repository.ShipmentRepository;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LazyLoadingTest {

    @Autowired
    private ShipmentRepository shipmentRepository;


    @Test
    public void lazyErisim_sessionKapaliykenPatlar(){

        var foundShipment = shipmentRepository.findAll().getFirst();

        assertThrows(LazyInitializationException.class,()->{
            shipmentRepository.findById(foundShipment.getId()).map(shipmentEntity -> shipmentEntity.getEvents().size());
        });
    }

    @Test
    public void getSizeDemo() {
        var foundShipment = shipmentRepository.findAll().getFirst();

        var events = shipmentRepository.findByIdWithEvents(foundShipment.getId()).orElseThrow();
        assertTrue(events.getEvents().size() > 0);

    }

}
