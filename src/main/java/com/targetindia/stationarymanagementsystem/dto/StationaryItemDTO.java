package com.targetindia.stationarymanagementsystem.dto;

import lombok.Data;

@Data
public class StationaryItemDTO {
    private String itemName;
    private Integer quantity;
    private Boolean returnable;
    private Integer maxDays;
}
