package com.targetindia.stationarymanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "transaction")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "transaction_id")
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private StationaryItem stationaryItem;

    @Temporal(TemporalType.DATE)
    @Column(name="transaction_date")
    private Date transactionDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    private Date returnDate;

    private Boolean returned;
}
