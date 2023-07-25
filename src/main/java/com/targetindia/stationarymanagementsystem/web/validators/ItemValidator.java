package com.targetindia.stationarymanagementsystem.web.validators;

import com.targetindia.stationarymanagementsystem.dto.StationaryItemDTO;

public class ItemValidator {
    public static Boolean isItemValid(StationaryItemDTO stationaryItemDTO){
        if(stationaryItemDTO.getItemName() == null || stationaryItemDTO.getItemName().isBlank()) return false;
        if(stationaryItemDTO.getQuantity() == null) return false;
        if(stationaryItemDTO.getReturnable() == null) return false;
        if(stationaryItemDTO.getReturnable() == true && stationaryItemDTO.getMaxDays() == null) return false;
        return true;
    }
}
