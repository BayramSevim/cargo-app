package com.kargo.api;

import com.kargo.domain.ShipmentStatus;
import com.kargo.infrastructure.persistence.entity.*;
import com.kargo.infrastructure.persistence.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
@Order(1)
public class DataSeeder implements CommandLineRunner {

    private CustomerRepository customerRepository;
    private ShipmentRepository shipmentRepository;
    private ShipmentEventRepository shipmentEventRepository;
    private CourierRepository courierRepository;
    private BranchRepository branchRepository;

    public DataSeeder(BranchRepository branchRepository, CourierRepository courierRepository, CustomerRepository customerRepository, ShipmentEventRepository shipmentEventRepository, ShipmentRepository shipmentRepository) {
        this.branchRepository = branchRepository;
        this.courierRepository = courierRepository;
        this.customerRepository = customerRepository;
        this.shipmentEventRepository = shipmentEventRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (shipmentRepository.count() > 0){
            return;
        }
        BranchEntity branch1 = new BranchEntity(
                "Şube-1",
                 "İzmir",
                Instant.now()
        );

        BranchEntity branch2 = new BranchEntity(
                "Şube-2",
                "İstanbul",
                Instant.now()
        );

        branchRepository.save(branch1);
        branchRepository.save(branch2);


        CourierEntity courier1 = new CourierEntity(
                "Hamit Çalışkan",
                "5434232",
                branch1,
                Instant.now()
        );
        CourierEntity courier2 = new CourierEntity(
                "Mehmet Çalışmaz",
                "5434232",
                branch1,
                Instant.now()
        );
        CourierEntity courier3 = new CourierEntity(
                "Ali Veli",
                "5434232",
                branch1,
                Instant.now()
        );
        courierRepository.save(courier1);
        courierRepository.save(courier2);
        courierRepository.save(courier3);

        CustomerEntity customer1 = new CustomerEntity(
                "Bayram Sevim",
                "bayram@gmail.com",
                "544312341",
                Instant.now()

        );
        CustomerEntity customer2 = new CustomerEntity(
                "Fatma Sevim",
                "fatma@gmail.com",
                "534123412",
                Instant.now()

        );
        CustomerEntity customer3 = new CustomerEntity(
                "Ahmet Sevim",
                "ahmet@gmail.com",
                "53123412",
                Instant.now()

        );
        CustomerEntity customer4 = new CustomerEntity(
                "Gizem Dilara Sert",
                "gizem@gmail.com",
                "53123412",
                Instant.now()

        );
        CustomerEntity customer5 = new CustomerEntity(
                "Çınar Işık",
                "cinar@gmail.com",
                "53123412",
                Instant.now()

        );
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
        customerRepository.save(customer5);

        ShipmentEntity shipment1 = new ShipmentEntity(
                "TRK100000001",
                ShipmentStatus.CREATED,
                List.of(),
                customer1,
                new BigDecimal("1.25"),
                new BigDecimal("145.90"),
                Instant.parse("2026-08-01T08:00:00Z")
        );

        ShipmentEntity shipment2 = new ShipmentEntity(
                "TRK100000002",
                ShipmentStatus.IN_TRANSIT,
                List.of(),
                customer2,
                new BigDecimal("3.80"),
                new BigDecimal("289.50"),
                Instant.parse("2026-08-02T09:15:00Z")
        );

        ShipmentEntity shipment3 = new ShipmentEntity(
                "TRK100000003",
                ShipmentStatus.OUT_FOR_DELIVERY,
                List.of(),
                customer3,
                new BigDecimal("0.75"),
                new BigDecimal("99.99"),
                Instant.parse("2026-08-03T10:30:00Z")
        );

        ShipmentEntity shipment4 = new ShipmentEntity(
                "TRK100000004",
                ShipmentStatus.DELIVERED,
                List.of(),
                customer4,
                new BigDecimal("6.40"),
                new BigDecimal("415.00"),
                Instant.parse("2026-08-04T11:45:00Z")
        );

        ShipmentEntity shipment5 = new ShipmentEntity(
                "TRK100000005",
                ShipmentStatus.RETURNED,
                List.of(),
                customer5,
                new BigDecimal("2.10"),
                new BigDecimal("180.75"),
                Instant.parse("2026-08-05T13:20:00Z")
        );

        shipment1.setBranch(branch1);
        shipment1.setCourier(courier1);

        shipment2.setBranch(null);
        shipment2.setCourier(null);

        shipment3.setBranch(branch2);
        shipment3.setCourier(courier1);

        shipment4.setBranch(null);
        shipment4.setCourier(null);

        shipment5.setBranch(branch1);
        shipment5.setCourier(courier2);

        shipmentRepository.save(shipment1);
        shipmentRepository.save(shipment2);
        shipmentRepository.save(shipment3);
        shipmentRepository.save(shipment4);
        shipmentRepository.save(shipment5);

        List<ShipmentEventEntity> events = List.of(
                new ShipmentEventEntity(shipment1, ShipmentStatus.CREATED, "Gönderi oluşturuldu.", Instant.parse("2026-08-01T08:15:00Z")),
                new ShipmentEventEntity(shipment1, ShipmentStatus.PICKED_UP, "Kurye paketi teslim aldı.", Instant.parse("2026-08-01T10:30:00Z")),
                new ShipmentEventEntity(shipment1, ShipmentStatus.IN_TRANSIT, "İstanbul aktarma merkezine ulaştı.", Instant.parse("2026-08-01T17:45:00Z")),
                new ShipmentEventEntity(shipment1, ShipmentStatus.AT_BRANCH, "İzmir dağıtım merkezinde.", Instant.parse("2026-08-02T05:20:00Z")),
                new ShipmentEventEntity(shipment1, ShipmentStatus.OUT_FOR_DELIVERY, "Teslimata çıktı.", Instant.parse("2026-08-02T08:10:00Z")),
                new ShipmentEventEntity(shipment2, ShipmentStatus.CREATED, "Sipariş oluşturuldu.", Instant.parse("2026-08-02T09:00:00Z")),
                new ShipmentEventEntity(shipment2, ShipmentStatus.PICKED_UP, "Şubeden teslim alındı.", Instant.parse("2026-08-02T11:15:00Z")),
                new ShipmentEventEntity(shipment2, ShipmentStatus.IN_TRANSIT, "Ankara aktarma merkezine sevk edildi.", Instant.parse("2026-08-02T18:00:00Z")),
                new ShipmentEventEntity(shipment2, ShipmentStatus.RETURNED, "Alıcı adreste bulunamadı.", Instant.parse("2026-08-03T14:25:00Z")),

                new ShipmentEventEntity(shipment3, ShipmentStatus.CREATED, "Gönderi sisteme kaydedildi.", Instant.parse("2026-08-03T07:50:00Z")),
                new ShipmentEventEntity(shipment3, ShipmentStatus.PICKED_UP, "Kurye teslim aldı.", Instant.parse("2026-08-03T10:05:00Z")),
                new ShipmentEventEntity(shipment3, ShipmentStatus.IN_TRANSIT, "Transfer merkezinde.", Instant.parse("2026-08-03T21:30:00Z")),
                new ShipmentEventEntity(shipment3, ShipmentStatus.AT_BRANCH, "Bursa dağıtım merkezinde.", Instant.parse("2026-08-04T04:10:00Z")),
                new ShipmentEventEntity(shipment3, ShipmentStatus.OUT_FOR_DELIVERY, "Teslimat aracına yüklendi.", Instant.parse("2026-08-04T08:45:00Z")),
                new ShipmentEventEntity(shipment3, ShipmentStatus.DELIVERED, "Alıcıya teslim edildi.", Instant.parse("2026-08-04T11:20:00Z")),

                new ShipmentEventEntity(shipment4, ShipmentStatus.CREATED, "Kargo kabul edildi.", Instant.parse("2026-08-04T08:30:00Z")),
                new ShipmentEventEntity(shipment4, ShipmentStatus.PICKED_UP, "Şubeden çıkış yaptı.", Instant.parse("2026-08-04T11:00:00Z")),
                new ShipmentEventEntity(shipment4, ShipmentStatus.RETURNED, "Göndericiye iade edildi.", Instant.parse("2026-08-06T15:40:00Z")),

                new ShipmentEventEntity(shipment5, ShipmentStatus.CREATED, "Yeni gönderi oluşturuldu.", Instant.parse("2026-08-05T09:10:00Z")),
                new ShipmentEventEntity(shipment5, ShipmentStatus.PICKED_UP, "Kurye teslim aldı.", Instant.parse("2026-08-05T12:00:00Z"))
        );

        shipmentEventRepository.saveAll(events);
    }
}
