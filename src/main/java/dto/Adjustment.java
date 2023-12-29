package dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Table(name = "adjustments")
public class Adjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long Id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deliveryId", referencedColumnName = "deliveryId")
    private final Delivery delivery;
    private Date modificationTime;
    private double value;

    public Adjustment(Long id, Delivery delivery, Date modifiedTimestamp, double value) {
        Id = id;
        this.delivery = delivery;
        this.modificationTime = modificationTime;
        this.value = value;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Date getModifiedTimestamp() {
        return modificationTime;
    }

    public void setModifiedTimestamp(Date modifiedTimestamp) {
        this.modificationTime = modificationTime;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Delivery getDelivery() {
        return delivery;
    }


}




