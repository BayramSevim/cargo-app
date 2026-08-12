package com.kargo.api.exception;

import com.kargo.domain.exception.ShipmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ShipmentNotFoundException.class)
    public ProblemDetail shipmentNotFoundException(ShipmentNotFoundException shipmentNotFoundException){
        URI uri = URI.create("https://kargo.com/errors/shipment-not-found");
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Gönderi bulunamadı");
        problemDetail.setType(uri);
        problemDetail.setProperty("trackingNumber",shipmentNotFoundException.getTrackingNumber());
        problemDetail.setDetail("Bu barkoda ait bir gönderi bulunamadı: " + shipmentNotFoundException.getTrackingNumber());

        return problemDetail;
    }
}
