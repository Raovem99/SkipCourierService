package com.skip.dish.Controller;

import com.skip.dish.Service.DeliveryTransactionService;
import com.skip.dish.domain.DeliveryTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courier-statements")
public class CourierStatementController {

    @Autowired
    private DeliveryTransactionService deliveryTransactionService;

    @GetMapping("/{courierId}")
    public DeliveryTransaction getWeeklyStatement(
            @PathVariable String courierId) {
        try {
            DeliveryTransaction result = deliveryTransactionService.getWeeklyStatementForCourier(courierId);
            return new ResponseEntity<>(result, HttpStatus.OK).getBody();
        } catch (Exception e) {
            // Log the exception details
            System.err.println("Error: " + e.getMessage());
            // Return an error response
        }
        return deliveryTransactionService.getWeeklyStatementForCourier(courierId);
    }

    @GetMapping("/getAllDeliveryStatement")
    public DeliveryTransaction getAllDeliveryStatement() {
            try {
        DeliveryTransaction result = deliveryTransactionService.getAllDeliveries();
        return new ResponseEntity<>(result, HttpStatus.OK).getBody();
    } catch (Exception e) {
        // Log the exception details
        System.err.println("Error: " + e.getMessage());
    }
     {
        return deliveryTransactionService.getAllDeliveries();
    }
    }
}

