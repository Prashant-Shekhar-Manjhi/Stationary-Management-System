package com.targetindia.stationarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Table(name = "transaction")
@Entity
public class Transaction{

    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "transaction_id")
    private Integer transactionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "item_id")
    private StationaryItem stationaryItem;

    @JsonIgnore
    @Column(name = "item_quantity")
    private Integer withdrawnQuantity;

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    @Column(name="transaction_date")
    private Date transactionDate = new Date();

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    private Date returnDate;

    @JsonIgnore
    private Boolean returned;
}
