package com.kargo.api.controller;

import com.kargo.api.dto.response.ShipmentResponse;
import com.kargo.api.service.ShipmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/{trackingNumber}")
    public ShipmentResponse getShipmentByTrackingNumber(@PathVariable String trackingNumber){
        return shipmentService.getShipmentByTrackingNumber(trackingNumber);
    }

}
