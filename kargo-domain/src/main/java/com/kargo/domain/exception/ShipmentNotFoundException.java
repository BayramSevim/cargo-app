package com.kargo.domain.exception;

public class ShipmentNotFoundException extends RuntimeException {
    private final String trackingNumber;

    public ShipmentNotFoundException(String message,String trackingNumber) {
        super(message);
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }
}
