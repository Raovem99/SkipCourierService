package com.skip.dish.domain;

import com.skip.dish.dao.dto.Delivery;

import java.util.List;

public class DeliveryTransaction {
    private List<Delivery> deliveries;
    private double value;

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
