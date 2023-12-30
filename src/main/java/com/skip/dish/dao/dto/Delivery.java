package com.skip.dish.dao.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "deliveries")
@SuppressWarnings("serial")
public class Delivery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryId")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "courierId", referencedColumnName = "courierId")

    private  Courier courier;
    private Date createdTime;
    private double value;

    public Delivery(Long id, Courier courier, Date createdTimestamp, double value) {
        this.id = id;
        this.courier = courier;
        this.createdTime = createdTime;
        this.value = value;
    }

    public Delivery() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Date getCreatedTimestamp() {
        return createdTime;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTime = createdTime;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }



    }




