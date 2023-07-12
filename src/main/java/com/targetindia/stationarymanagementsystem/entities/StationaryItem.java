package com.targetindia.stationarymanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
}
