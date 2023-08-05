package com.targetindia.stationarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StationaryItemDTO {
    private String itemName;
    private Integer quantity;
    private Boolean returnable;
    private Integer maxDays;
}
