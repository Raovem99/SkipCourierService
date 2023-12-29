package dto;

import domain.DeliveryTransaction;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "deliveries")
public class Delivery implements Callback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {

    }
}




