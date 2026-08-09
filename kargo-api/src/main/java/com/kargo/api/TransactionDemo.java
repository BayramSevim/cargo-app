package com.kargo.api;

import com.kargo.api.service.ShipmentService;
import com.kargo.infrastructure.persistence.repository.ShipmentEventRepository;
import com.kargo.infrastructure.persistence.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class TransactionDemo implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(TransactionDemo.class);


    private final ShipmentService shipmentService;
    private final ShipmentRepository shipmentRepository;
    private final ShipmentEventRepository shipmentEventRepository;

    public TransactionDemo(ShipmentService shipmentService, ShipmentRepository shipmentRepository, ShipmentEventRepository shipmentEventRepository) {
        this.shipmentService = shipmentService;
        this.shipmentRepository = shipmentRepository;
        this.shipmentEventRepository = shipmentEventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long oncekiGonderi = shipmentRepository.count();
        long oncekiEvent = shipmentEventRepository.count();
        logger.info("ONCE  -> gonderi: {}, event: {}", oncekiGonderi, oncekiEvent);
        shipmentService.gonderiOlustur("TX-" + System.currentTimeMillis());
        logger.info("SONRA -> gonderi: {}, event: {}", shipmentRepository.count(), shipmentEventRepository.count());

        try {
//            shipmentService.gonderiOlusturVePatla("TX-" + System.currentTimeMillis());
            shipmentService.gonderiOlusturVeCheckedPatla("TX-" + System.currentTimeMillis());
        }
        catch (Exception e){
            long oncekiGonderi2 = shipmentRepository.count();
            long oncekiEvent2 = shipmentEventRepository.count();
            logger.error("Hata:", e);
            logger.info("ONCE  -> gonderi: {}, event: {}", oncekiGonderi2, oncekiEvent2);
            logger.info("SONRA -> gonderi: {}, event: {}", shipmentRepository.count(), shipmentEventRepository.count());
        }
    }
}
