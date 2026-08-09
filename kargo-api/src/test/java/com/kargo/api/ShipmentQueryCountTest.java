package com.kargo.api;

import com.kargo.infrastructure.persistence.repository.ShipmentRepository;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ShipmentQueryCountTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private ShipmentRepository shipmentRepository;


    @Test
    public void shipment_test_demo(){
        Statistics statistics =  entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();

        var findAll = shipmentRepository.findAll();
        findAll.stream().map(shipmentEntity -> shipmentEntity.getEvents().size()).toList();
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);


    }

}
