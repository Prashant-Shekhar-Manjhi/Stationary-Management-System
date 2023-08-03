package com.targetindia.stationarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stationary_item")
public class StationaryItem {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(generator = "increment")
    private Integer itemId;

    @Column(name = "item_name", unique = true)
    private String itemName;

    @Column(name = "item_quantity")
    private Integer quantity;

    @Column(name = "returnable")
    private Boolean returnable;

    @Column(name = "max_days")
    private Integer maxDays;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "item_id")
    private List<Transaction> transactions;
}
