package com.skip.dish.Controller;

import com.skip.dish.Service.DeliveryTransactionService;
import com.skip.dish.domain.DeliveryTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/delivery-transactions")
public class DeliveryTransactionController {

    @Autowired
    private DeliveryTransactionService deliveryTransactionService;

    @GetMapping
    public DeliveryTransaction getDeliveryTransactions(
            @RequestParam String courierId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            DeliveryTransaction result = deliveryTransactionService.getDeliveryTransactionsByCourierAndPeriod(courierId, startDate, endDate);
            return new ResponseEntity<>(result, HttpStatus.OK).getBody();
        } catch (Exception e) {
            // Log the exception details
            System.err.println("Error: " + e.getMessage());
        }
        return deliveryTransactionService.getDeliveryTransactionsByCourierAndPeriod(courierId, startDate, endDate);
    }
}

